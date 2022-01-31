package com.thelis.test3.model

import android.graphics.Bitmap

class AppListModel(
    private var name: String,
    private var icon: Bitmap,
    private var size: String,
    private var installedDate: Long,
    private var sdkVer: Int,
) {
    @JvmName("getName1")
    fun getName(): String {
        return name
    }

    fun getIcon(): Bitmap {
        return icon
    }

    fun getSize(): String {
        return size
    }

    fun getInstallationDate(): Long {
        return installedDate
    }

    fun getSdkVersion(): Int {
        return sdkVer
    }
}