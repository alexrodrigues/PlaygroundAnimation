package br.com.rodriguesalex.cast

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.content_main.*
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.source.MediaSource
import android.view.animation.TranslateAnimation
import android.util.DisplayMetrics
import android.widget.RelativeLayout






class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
//        p0?.progress?.let {
//            val float = (it.toFloat()/100f)
//            container.alpha =  float
//            tvAlphaValue.text = "Alpha: $float"
//        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            moveToCenter()
        }


    }

    private fun moveToCenter() {

        val dm = DisplayMetrics()
        // this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        windowManager.defaultDisplay.getMetrics(dm)
        val statusBarOffset = dm.heightPixels - root.measuredHeight

        val originalPos = IntArray(2)
        ivCoverPrimary.getLocationOnScreen(originalPos)

        var xDest = dm.widthPixels / 2
        xDest -= ivCoverSecundary.getMeasuredWidth() / 2
        val yDest = dm.heightPixels / 2 - ivCoverSecundary.getMeasuredHeight() / 2 - statusBarOffset

        val anim = TranslateAnimation(0f, (xDest - originalPos[0]).toFloat(), 0f, (yDest - originalPos[1]).toFloat())
        anim.duration = 1000
        anim.fillAfter = true
        ivCoverPrimary.startAnimation(anim)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
