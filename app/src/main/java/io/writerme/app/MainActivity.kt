package io.writerme.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.writerme.app.ui.navigation.BookmarksScreen
import io.writerme.app.ui.navigation.GreetingScreen
import io.writerme.app.ui.navigation.HomeScreen
import io.writerme.app.ui.navigation.NoteScreen
import io.writerme.app.ui.navigation.RegistrationScreen
import io.writerme.app.ui.navigation.SettingsScreen
import io.writerme.app.ui.screen.BookmarksScreen
import io.writerme.app.ui.screen.GreetingScreen
import io.writerme.app.ui.screen.HomeScreen
import io.writerme.app.ui.screen.NoteScreen
import io.writerme.app.ui.screen.RegistrationScreen
import io.writerme.app.ui.screen.SettingsScreen
import io.writerme.app.ui.theme.WriterMeTheme
import io.writerme.app.utils.Const
import io.writerme.app.utils.LocaleUtils
import io.writerme.app.viewmodel.BookmarksViewModel
import io.writerme.app.viewmodel.HomeViewModel
import io.writerme.app.viewmodel.MainViewModel
import io.writerme.app.viewmodel.NoteViewModel
import io.writerme.app.viewmodel.RegistrationViewModel
import io.writerme.app.viewmodel.SettingsViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    private fun onLinkClicked(url: String) {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } catch (e: Exception) {
            Log.e(tag, "", e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            // Resolves start route on IO thread — no main-thread blocking.
            val mainViewModel = hiltViewModel<MainViewModel>()
            val startRoute by mainViewModel.startRoute.collectAsStateWithLifecycle()

            WriterMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent,
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Background renders on the very first frame, before
                        // the route is even known — the app feels instant.
                        Image(
                            painter = painterResource(id = R.drawable.background_main),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        val context = LocalContext.current

                        // NavHost only appears once the route is resolved
                        // (typically < 100 ms on IO thread after DB warm-up).
                        startRoute?.let { route ->
                            NavHost(
                                navController = navController,
                                startDestination = route
                            ) {
                                composable(GreetingScreen.route) {
                                    GreetingScreen {
                                        navController.navigate(RegistrationScreen.route)
                                    }
                                }

                                composable(RegistrationScreen.route) {
                                    val registrationViewModel = hiltViewModel<RegistrationViewModel>()

                                    RegistrationScreen(
                                        saveName = registrationViewModel::saveName,
                                        proceedToNextScreen = {
                                            navController.navigate(HomeScreen.route)
                                        }
                                    )
                                }

                                composable(HomeScreen.route) {
                                    // Scoped to this back-stack entry: survives
                                    // navigation to sub-screens and back.
                                    val homeViewModel = hiltViewModel<HomeViewModel>()

                                    HomeScreen(
                                        stateFlow = homeViewModel.homeStateFlow,
                                        toggleSearchMode = homeViewModel::toggleSearchMode,
                                        openTasksScreen = {
                                            Toast.makeText(
                                                context,
                                                R.string.sorry_pending,
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },
                                        openBookmarksScreen = {
                                            navController.navigate(BookmarksScreen.route)
                                        },
                                        openSettingsScreen = {
                                            navController.navigate(SettingsScreen.route)
                                        },
                                        onNoteClick = {
                                            navController.navigate("${NoteScreen.navigationRoute}$it")
                                        },
                                        createNote = {
                                            navController.navigate(NoteScreen.navigationRoute)
                                        },
                                        onTabChosen = homeViewModel::onTabChosen,
                                        toggleImportance = homeViewModel::toggleImportance,
                                        deleteNote = homeViewModel::deleteNote
                                    )
                                }

                                composable(
                                    NoteScreen.route,
                                    arguments = listOf(
                                        navArgument(NoteScreen.NOTE_PARAM) {
                                            nullable = true
                                        }
                                    )
                                ) {
                                    val noteViewModel = hiltViewModel<NoteViewModel>()

                                    NoteScreen(
                                        noteState = noteViewModel.noteState,
                                        toggleHistoryMode = noteViewModel::toggleHistoryMode,
                                        toggleTopBarDropdownVisibility = noteViewModel::toggleTopBarDropdownVisibility,
                                        updateCoverImage = noteViewModel::updateCoverImage,
                                        showHashtagBar = noteViewModel::showHashtagBar,
                                        addNewTag = noteViewModel::addNewTag,
                                        deleteTag = noteViewModel::deleteTag,
                                        modifyHistory = noteViewModel::modifyHistory,
                                        saveChanges = noteViewModel::saveChanges,
                                        onComponentChange = noteViewModel::onComponentChange,
                                        addNewCheckBox = noteViewModel::addNewCheckBox,
                                        navigateBack = { navController.popBackStack() },
                                        addImageSection = noteViewModel::addImageSection,
                                        showDropdown = noteViewModel::showDropdown,
                                        dismissDropDown = noteViewModel::dismissDropDown,
                                        toggleDropDownHistoryMode = noteViewModel::toggleDropDownHistoryMode,
                                        addLinkSection = noteViewModel::addLinkSection,
                                        toggleAddLinkDialogVisibility = noteViewModel::toggleAddLinkDialogVisibility,
                                        toggleCheckbox = noteViewModel::toggleCheckbox,
                                        deleteSection = noteViewModel::deleteSection
                                    )
                                }

                                composable(BookmarksScreen.route) {
                                    val bookmarksViewModel = hiltViewModel<BookmarksViewModel>()

                                    BookmarksScreen(
                                        bookmarksState = bookmarksViewModel.bookmarksStateFlow,
                                        onFolderClicked = bookmarksViewModel::onFolderClicked,
                                        onLinkClicked = { onLinkClicked(it.url) },
                                        showCreateFolderDialog = bookmarksViewModel::showCreateFolderDialog,
                                        dismissCreateFolderDialog = bookmarksViewModel::dismissCreateFolderDialog,
                                        showCreateBookmarkDialog = bookmarksViewModel::showCreateBookmarkDialog,
                                        dismissCreateBookmarkDialog = bookmarksViewModel::dismissCreateBookmarkDialog,
                                        toggleFloatingDialog = bookmarksViewModel::toggleFloatingDialog,
                                        navigateToParentFolder = bookmarksViewModel::navigateToParentFolder,
                                        createBookmark = bookmarksViewModel::createBookmark,
                                        createFolder = bookmarksViewModel::createFolder,
                                        deleteFolder = bookmarksViewModel::deleteFolder,
                                        deleteBookmark = bookmarksViewModel::deleteBookmark,
                                        toggleFolderDropdown = bookmarksViewModel::toggleFolderDropdown,
                                        toggleBookmarkDropdown = bookmarksViewModel::toggleBookmarkDropdown,
                                        dismissScreen = { navController.popBackStack() }
                                    )
                                }

                                composable(SettingsScreen.route) {
                                    val settingsViewModel = hiltViewModel<SettingsViewModel>()

                                    SettingsScreen(
                                        uiState = settingsViewModel.settingsState,
                                        onLanguageChange = {
                                            LocaleUtils.setLocale(it)
                                            settingsViewModel.onLanguageChange(it)
                                        },
                                        onDarkModeChange = settingsViewModel::onDarkModeChange,
                                        onTermsClick = { onLinkClicked(Const.TERMS_LINK) },
                                        onCounterChange = settingsViewModel::onCounterChange,
                                        updateProfileImage = settingsViewModel::updateProfileImage,
                                        dismissScreen = { navController.popBackStack() },
                                        showExportSheet = settingsViewModel::showExportSheet,
                                        hideExportSheet = settingsViewModel::hideExportSheet,
                                        toggleExportNotes = settingsViewModel::toggleExportNotes,
                                        toggleExportBookmarks = settingsViewModel::toggleExportBookmarks,
                                        exportData = settingsViewModel::exportData,
                                        importData = settingsViewModel::importData,
                                        clearDataIoMessage = settingsViewModel::clearDataIoMessage
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
