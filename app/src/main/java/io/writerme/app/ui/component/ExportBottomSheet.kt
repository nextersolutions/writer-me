package io.writerme.app.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.writerme.app.R
import io.writerme.app.ui.theme.backgroundGrey
import io.writerme.app.ui.theme.cardBackground
import io.writerme.app.ui.theme.light
import io.writerme.app.ui.theme.strokeLight

@Composable
fun ExportBottomSheet(
    visible: Boolean,
    exportNotes: Boolean,
    exportBookmarks: Boolean,
    isExporting: Boolean,
    onToggleNotes: () -> Unit,
    onToggleBookmarks: () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onDismiss)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clickable(enabled = false, onClick = {}),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                backgroundColor = MaterialTheme.colors.cardBackground,
                elevation = 16.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(24.dp)
                ) {
                    // Handle
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(4.dp)
                            .background(
                                MaterialTheme.colors.strokeLight,
                                RoundedCornerShape(2.dp)
                            )
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_history),
                            contentDescription = null,
                            tint = MaterialTheme.colors.light,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.export),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.light
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.export_description),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.light.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Divider(color = MaterialTheme.colors.strokeLight)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Notes toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onToggleNotes),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.notes),
                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colors.light
                        )
                        Checkbox(
                            checked = exportNotes,
                            onCheckedChange = { onToggleNotes() },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.primary,
                                uncheckedColor = MaterialTheme.colors.light
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Bookmarks toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onToggleBookmarks),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.bookmarks),
                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colors.light
                        )
                        Checkbox(
                            checked = exportBookmarks,
                            onCheckedChange = { onToggleBookmarks() },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.primary,
                                uncheckedColor = MaterialTheme.colors.light
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (!isExporting && (exportNotes || exportBookmarks)) onConfirm()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.backgroundGrey
                        ),
                        enabled = (exportNotes || exportBookmarks) && !isExporting
                    ) {
                        if (isExporting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colors.light
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.export),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.light
                            )
                        }
                    }
                }
            }
        }
    }
}
