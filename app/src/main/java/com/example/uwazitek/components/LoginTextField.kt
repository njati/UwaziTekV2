//package com.example.uwazitek.components
//
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.material.Icon
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.VisualTransformation
//
//@Composable
//fun LoginTextField(
//    modifier: Modifier = Modifier,
//    value: String,
//    onValueChange: (String) -> Unit,
//    labelText: String,
//    leadingIcon: ImageVector? = null,
//    keyboardType: KeyboardType = KeyboardType.Text,
//    visualTransformation: VisualTransformation = VisualTransformation.None
//) {
//    OutlinedTextField(
//        modifier = modifier,
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(labelText) },
//
//            )
//}
package com.example.uwazitek.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions


@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        leadingIcon = leadingIcon?.let { { Icon(it, contentDescription = null) } },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(20.dp)
    )
}
