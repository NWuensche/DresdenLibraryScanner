package com.nwuensche.dresdenbarcodescanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.okButton
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever


class MainActivity : AppCompatActivity(), BarcodeRetriever {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val barcodeCapture = supportFragmentManager.findFragmentById(R.id.barcode) as BarcodeCapture
        barcodeCapture.setRetrieval(this)
        this.window.decorView.systemUiVisibility =  View.SYSTEM_UI_FLAG_FULLSCREEN

    }

    override fun onPermissionRequestDenied() {
        runOnUiThread {
            val builder = AlertDialog.Builder(this@MainActivity)
                    .setTitle("No Permission")
                    .setMessage("No Permission")
            builder.show()
        }
    }

    override fun onRetrieved(p0: Barcode?) {
        //val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        //toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
        val browserIntentSLUB = Intent(Intent.ACTION_VIEW, Uri.parse("https://katalogbeta.slub-dresden.de/?tx_find_find%5B__referrer%5D%5B%40extension%5D=Find&tx_find_find%5B__referrer%5D%5B%40vendor%5D=Subugoe&tx_find_find%5B__referrer%5D%5B%40controller%5D=Search&tx_find_find%5B__referrer%5D%5B%40action%5D=detail&tx_find_find%5B__referrer%5D%5Barguments%5D=YToxOntzOjI6ImlkIjtzOjEwOiIwMDAwNTkxODMwIjt97a1add00c8ea9fecec633de64429e759a830ce04&tx_find_find%5B__referrer%5D%5B%40request%5D=a%3A4%3A%7Bs%3A10%3A%22%40extension%22%3Bs%3A4%3A%22Find%22%3Bs%3A11%3A%22%40controller%22%3Bs%3A6%3A%22Search%22%3Bs%3A7%3A%22%40action%22%3Bs%3A6%3A%22detail%22%3Bs%3A7%3A%22%40vendor%22%3Bs%3A7%3A%22Subugoe%22%3B%7D5035da4f0ee67887a866b971ad4e1f5252f897fd&tx_find_find%5B__trustedProperties%5D=a%3A1%3A%7Bs%3A1%3A%22q%22%3Ba%3A1%3A%7Bs%3A7%3A%22default%22%3Bi%3A1%3B%7D%7D6b76be83b7db9370ad51d5ea02e98dc6e757fbe2&tx_find_find%5Bq%5D%5Bdefault%" +
                "5D=${p0!!.displayValue}#detail"))
            startActivity(browserIntentSLUB)

    }

    override fun onRetrievedMultiple(closetToClick: Barcode, barcodeGraphics: List<BarcodeGraphic>) {
    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>) {
        // when image is scanned and processed
    }

    override fun onRetrievedFailed(reason: String) {
        // in case of failure
    }

}
