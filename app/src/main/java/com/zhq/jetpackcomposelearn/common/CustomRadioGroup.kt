package com.zhq.jetpackcomposelearn.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @Author ZhangHuiQiang
 * @Date 2025/4/17 14:01
 * Description
 */

@Composable
fun CustomRadioGroup(
    title: String, list: List<String>,
    selectedIndex: Int = 0,
    onRadioSelectListener: (Int, String) -> Unit
) {
    val radioOptionList = list
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(radioOptionList[selectedIndex])
    }
    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 13.sp, color = Color.Black)
        VerticalSpace(width = 5.dp)
        radioOptionList.forEachIndexed { index, s ->
            Row(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .selectable(
                        selected = (s == selectedOption),
                        onClick = {
                            onOptionSelected(s)
                            onRadioSelectListener.invoke(index + 1, s)
                        },
                        role = Role.RadioButton,
                        //去除点击阴影水波纹
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                RadioButton(
                    selected = (s == selectedOption),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Yellow,
                        unselectedColor = Color.DarkGray
                    ),
                    modifier = Modifier.size(10.dp), onClick = null
                )
                VerticalSpace(width = 2.dp)
                Text(text = s, fontSize = 12.sp)
            }
        }

    }
}