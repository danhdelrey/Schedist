package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.times
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import java.math.RoundingMode

@Composable
fun TodoProgressBar(
    currentProgress : Float,
    inDetailMode : Boolean? = false
){
    val progressPercent = ((currentProgress*100).toBigDecimal().setScale(0, RoundingMode.HALF_EVEN))
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Column{
            if (inDetailMode!!){
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(
                        text = "Tiến độ:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$progressPercent%",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                LinearProgressIndicator(
                    progress = { currentProgress },
                    modifier = Modifier.weight(1f)
                )
                if (!inDetailMode)
                    Text(
                        text= "$progressPercent%",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start=20.dp))
            }
            if (inDetailMode){
                Spacer(
                    modifier = Modifier.padding(4.dp)
                )
            }

        }
    }


}