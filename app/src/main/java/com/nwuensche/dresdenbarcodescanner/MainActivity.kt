package com.nwuensche.dresdenbarcodescanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic
import com.google.android.gms.vision.barcode.Barcode
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.okButton
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever


class MainActivity : AppCompatActivity(), BarcodeRetriever {
    val PREFS_NAME = "BibDresdenFile"
    var showSBD: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val barcodeCapture = supportFragmentManager.findFragmentById(R.id.barcode) as BarcodeCapture
        barcodeCapture.setRetrieval(this)

        val settings = getSharedPreferences(PREFS_NAME, 0)
        showSBD = settings.getBoolean("showSBD", false)
    }

    override fun onPermissionRequestDenied() {
        runOnUiThread {
            val builder = AlertDialog.Builder(this@MainActivity)
                    .setTitle("Keine Berechtigung")
                    .setMessage("Keine Berechtigung")
            builder.show()
        }
    }

    override fun onRetrieved(p0: Barcode?) {
        //val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        //toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
        val browserIntentSLUB = Intent(Intent.ACTION_VIEW, Uri.parse("https://katalogbeta.slub-dresden.de/?tx_find_find%5B__referrer%5D%5B%40extension%5D=Find&tx_find_find%5B__referrer%5D%5B%40vendor%5D=Subugoe&tx_find_find%5B__referrer%5D%5B%40controller%5D=Search&tx_find_find%5B__referrer%5D%5B%40action%5D=detail&tx_find_find%5B__referrer%5D%5Barguments%5D=YToxOntzOjI6ImlkIjtzOjEwOiIwMDAwNTkxODMwIjt97a1add00c8ea9fecec633de64429e759a830ce04&tx_find_find%5B__referrer%5D%5B%40request%5D=a%3A4%3A%7Bs%3A10%3A%22%40extension%22%3Bs%3A4%3A%22Find%22%3Bs%3A11%3A%22%40controller%22%3Bs%3A6%3A%22Search%22%3Bs%3A7%3A%22%40action%22%3Bs%3A6%3A%22detail%22%3Bs%3A7%3A%22%40vendor%22%3Bs%3A7%3A%22Subugoe%22%3B%7D5035da4f0ee67887a866b971ad4e1f5252f897fd&tx_find_find%5B__trustedProperties%5D=a%3A1%3A%7Bs%3A1%3A%22q%22%3Ba%3A1%3A%7Bs%3A7%3A%22default%22%3Bi%3A1%3B%7D%7D6b76be83b7db9370ad51d5ea02e98dc6e757fbe2&tx_find_find%5Bq%5D%5Bdefault%" +
                "5D=${p0!!.displayValue}#detail"))
        if(showSBD) {

            runOnUiThread {
                alert {
                    title = "Zeige welchen Katalog?" //TODO Englisch

                    val browserIntentSBD = Intent(Intent.ACTION_VIEW, Uri.parse("https://katalog.bibo-dresden.de/webOPACClient/search.do;jsessionid=00B02B30080C6775EE8C6DB6611D6677?methodToCall=submit&CSId=3866N36S25e4c62c628253101d89b9456b5d25930d1c392d&methodToCallParameter=submitSearch&searchCategories%5B0%5D=-1&searchString%5B0%5D=${p0!!.displayValue}&callingPage=searchParameters&selectedViewBranchlib=0&selectedSearchBranchlib=&searchRestrictionID%5B0%5D=8&searchRestrictionValue1%5B0%5D=&searchRestrictionID%5B1%5D=6&searchRestrictionValue1%5B1%5D=&searchRestrictionID%5B2%5D=3&searchRestrictionValue1%5B2%5D=&searchRestrictionValue2%5B2%5D="))
                    positiveButton("SLUB") { startActivity(browserIntentSLUB) }
                    negativeButton("SBD") { startActivity(browserIntentSBD) }
                }.show()
            }
        }
        else {
            startActivity(browserIntentSLUB)
        }

    }

    override fun onRetrievedMultiple(closetToClick: Barcode, barcodeGraphics: List<BarcodeGraphic>) {
    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>) {
        // when image is scanned and processed
    }

    override fun onRetrievedFailed(reason: String) {
        // in case of failure
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.findItem(R.id.addSBD).isChecked = showSBD
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.addSBD -> {
                val settings = getSharedPreferences(PREFS_NAME, 0)
                val editor = settings.edit()
                if(item.isChecked) {
                    editor.putBoolean("showSBD", false)
                    showSBD = false
                    item.isChecked = false
                }
                else {
                    editor.putBoolean("showSBD", true)
                    showSBD = true
                    item.isChecked = true
                }
                editor.apply()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        //TODO Fix Layout Bottom
    }

}
