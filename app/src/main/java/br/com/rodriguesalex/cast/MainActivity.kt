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




class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        p0?.progress?.let {
            val float = (it.toFloat()/100f)
            container.alpha =  float
            tvAlphaValue.text = "Alpha: $float"
        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        setupVideo()

        seekBar.setOnSeekBarChangeListener(this)

        Glide.with(this).load(R.drawable.example).into(ivGif)
    }

    private fun setupVideo() {
        val dataSourceFactory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), DefaultBandwidthMeter())

        val uri = Uri.parse("https://s3-sa-east-1.amazonaws.com/bff-ms-music-dev/dev/polo.mp4")
        val mediaSource = ExtractorMediaSource(uri, dataSourceFactory, DefaultExtractorsFactory(), null, null)
        val player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        player.prepare(mediaSource)
        player.playWhenReady = true
        exoplayer.player = player
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
