package com.example.carstockapplication.presentation.screens

import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carstockapplication.domain.model.Vehicle
import com.example.carstockapplication.presentation.viewmodel.VehicleViewModel
import com.example.carstockapplication.ui.theme.AccentGold
import com.example.carstockapplication.ui.theme.AccentOrange
import com.example.carstockapplication.ui.theme.Background
import com.example.carstockapplication.ui.theme.BorderColor
import com.example.carstockapplication.ui.theme.StatusGreen
import com.example.carstockapplication.ui.theme.StatusRed
import com.example.carstockapplication.ui.theme.StatusYellow
import com.example.carstockapplication.ui.theme.Surface1
import com.example.carstockapplication.ui.theme.Surface2
import com.example.carstockapplication.ui.theme.TextMuted
import com.example.carstockapplication.ui.theme.TextPrimary
import kotlin.collections.emptyList

@Composable
fun VehicleListScreen(viewModel: VehicleViewModel) {

    val pageable by viewModel.vehicles.observeAsState(null)
    val vehicles = pageable?.content ?: emptyList()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState(null)

    VehicleListContent(
        vehicles = vehicles,
        isLoading = isLoading,
        error = error,
        totalElements = pageable?.metadata?.totalElements,
        onRetry = { viewModel.loadVehicles() }
    )

}


@Composable
fun VehicleListContent(
    vehicles: List<Vehicle>,
    isLoading: Boolean,
    error: String?,
    totalElements: Int?,
    onRetry: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        onRetry()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Orb decorativo
        Box(
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(AccentOrange.copy(alpha = 0.08f), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(56.dp))

            // ── Header ────────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { -30 }
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(AccentOrange, AccentGold),
                                        start = Offset(0f, 0f),
                                        end = Offset(80f, 80f)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("CS", color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.sp)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Estoque", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)
                            Text("Veículos disponíveis", color = TextMuted, fontSize = 12.sp)
                        }
                        Spacer(Modifier.weight(1f))
                        if (!isLoading) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Surface2)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${totalElements} veíc.",
                                    color = AccentOrange,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(BorderColor)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Box( modifier = Modifier.fillMaxWidth()) {
                ButtonCreateVehicle(modifier = Modifier.align(Alignment.TopEnd))
            }

            // ── Estados ───────────────────────────────────────────────────
            when {
                isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = AccentOrange, strokeWidth = 2.dp)
                            Spacer(Modifier.height(12.dp))
                            Text("Carregando estoque...", color = TextMuted, fontSize = 13.sp)
                        }
                    }
                }

                error != null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Surface1)
                                .padding(24.dp)
                        ) {
                            Text("⚠", fontSize = 32.sp)
                            Spacer(Modifier.height(8.dp))
                            Text("Erro ao carregar", color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(error ?: "", color = TextMuted, fontSize = 12.sp)
                            Spacer(Modifier.height(16.dp))
                            Button(
                                onClick = { onRetry() },
                                colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Tentar novamente", color = Color.White, fontSize = 13.sp)
                            }
                        }
                    }
                }

                vehicles.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = TextMuted, modifier = Modifier.size(48.dp))
                            Spacer(Modifier.height(12.dp))
                            Text("Nenhum veículo encontrado", color = TextMuted, fontSize = 14.sp)
                        }
                    }
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        itemsIndexed(vehicles) { index, vehicle ->
                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn(tween(400, delayMillis = index * 60)) +
                                        slideInVertically(tween(400, delayMillis = index * 60)) { 40 }
                            ) {
                                VehicleCard(vehicle)
                            }
                        }
                        item { Spacer(Modifier.height(16.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun VehicleCard(vehicle: Vehicle) {
    val statusColor = when (vehicle.status.uppercase()) {
        "AVAILABLE", "DISPONIVEL" -> StatusGreen
        "SOLD", "VENDIDO"         -> StatusRed
        else                      -> StatusYellow
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Surface1)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícone
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Surface2),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Edit,
                contentDescription = null,
                tint = AccentOrange,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        // Info principal
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vehicle.name,
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${vehicle.brandName} · ${vehicle.modelYear}",
                color = TextMuted,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Status badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(vehicle.status, color = statusColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                // Combustível badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Surface2)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(vehicle.fuelType, color = TextMuted, fontSize = 10.sp)
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        // Preço
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "R$",
                color = TextMuted,
                fontSize = 10.sp
            )
            Text(
                text = formatPrice(vehicle.salePrice),
                color = AccentGold,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ButtonCreateVehicle(modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text("Cadastrar Veículo")
    }
}


@Preview(showBackground = true)
@Composable
fun VehiclePreview() {
    VehicleListContent(
        vehicles = listOf(
            Vehicle(name = "Corolla", brandName = "Toyota", brandId = "qsdasdasd", modelName = "Corolla", modelId = "qdassd", versionName = "XEI", versionId = "12312312", id = "123123", modelYear = 2010, manufacturerYear = 2010, color = "Branco", status = "AVAILABLE", chassis = "ASDASDASD", mileage = 190, fuelType = "GASOLINE", costPrice = 28.000, salePrice = 30.000, entryDate = "", optionals = emptyList(), licensePlate = "EAJ-5D-21"),
            Vehicle(name = "Corolla", brandName = "Toyota", brandId = "qsdasdasd", modelName = "Corolla", modelId = "qdassd", versionName = "XEI", versionId = "12312312", id = "123123", modelYear = 2010, manufacturerYear = 2010, color = "Branco", status = "AVAILABLE", chassis = "ASDASDASD", mileage = 190, fuelType = "GASOLINE", costPrice = 28.000, salePrice = 30.000, entryDate = "", optionals = emptyList(), licensePlate = "EAJ-5D-21"),
        ),
        onRetry = {},
        totalElements = 10,
        error = null,
        isLoading = false
    )
}

private fun formatPrice(price: Double): String {
    return "%,.0f".format(price).replace(",", ".")
}