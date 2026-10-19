package com.venice.ffpanel

data class FeatureState(
    val name: String,
    val desc: String,
    val enabled: Boolean
)

object Config {

    fun defaultFeatures() = listOf(
        FeatureState("No Recoil", "Retroceso a cero absoluto", true),
        FeatureState("Aim Assist MAX", "Asistencia de apuntado al 100%", true),
        FeatureState("Auto Headshot", "El disparo se pega a la cabeza", true),
        FeatureState("Less Spread", "Dispersion a cero, bala recta", true),
        FeatureState("Aim Lock duro", "Bloqueo agresivo del objetivo", true),
        FeatureState("FPS Boost", "Baja sombras y anti-aliasing", true)
    )

    fun balanced() = defaultFeatures().map {
        if (it.name == "Aim Lock duro") it.copy(enabled = false) else it
    }

    fun soft() = defaultFeatures().map {
        it.copy(enabled = it.name == "No Recoil" || it.name == "FPS Boost")
    }

    fun buildIni(features: List<FeatureState>, smooth: Float): String {
        fun on(name: String) =
            if (features.firstOrNull { it.name == name }?.enabled == true) 1 else 0

        return buildString {
            appendLine("; ===== FF PANEL - MAX =====")
            appendLine("; Generado por la app. Backup automatico: UserCustom.ini.bak")
            appendLine()
            appendLine("[UserCustom DeviceProfile]")
            appendLine("+CVars=r.NoRecoil=${on("No Recoil")}")
            appendLine("+CVars=r.RecoilScale=0.0")
            appendLine("+CVars=r.SpreadScale=0.0")
            appendLine("+CVars=r.AimAssist=${on("Aim Assist MAX")}")
            appendLine("+CVars=r.AimAssistStrength=100")
            appendLine("+CVars=r.AutoHeadshot=${on("Auto Headshot")}")
            appendLine("+CVars=r.HeadshotScale=1.0")
            appendLine("+CVars=r.AimLock=${on("Aim Lock duro")}")
            appendLine("+CVars=r.AimLockSmooth=$smooth")
            appendLine("+CVars=r.AimLockBone=Head")
            appendLine("+CVars=r.AimLockFov=60")
            appendLine("+CVars=r.Shadows=${if (on("FPS Boost") == 1) 0 else 1}")
            appendLine("+CVars=r.AntiAliasing=0")
            appendLine("+CVars=r.MobileContentScaleFactor=1.0")
            appendLine("+CVars=r.FPS=90")
        }
    }
}
