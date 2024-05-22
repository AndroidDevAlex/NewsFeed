
package com.example.newsfeed.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsfeed.R

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .height(150.dp)
                    .background(
                        Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(painter = painterResource(id = R.drawable.error) ,
                    contentDescription ="icon Error" , tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp))

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.error_during_update),
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}