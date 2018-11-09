package br.com.rodriguesalex.cast

import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.animation.TranslateAnimation
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.support.v4.media.session.MediaControllerCompat.setMediaController
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    private val formationSparkles = intArrayOf(R.drawable.formacao_1, R.drawable.formacao_2,
                        R.drawable.formacao_3, R.drawable.formacao_4, R.drawable.formacao_5,
            R.drawable.formacao_6, R.drawable.formacao_7, R.drawable.formacao_8, R.drawable.formacao_9,
            R.drawable.formacao_10, R.drawable.formacao_11, R.drawable.formacao_12, R.drawable.formacao_13,
            R.drawable.formacao_14, R.drawable.formacao_15, R.drawable.formacao_16, R.drawable.formacao_17,
            R.drawable.formacao_18, R.drawable.formacao_20, R.drawable.formacao_21, R.drawable.formacao_22,
            R.drawable.formacao_23, R.drawable.formacao_24, R.drawable.formacao_25, R.drawable.formacao_26)

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
            playSparkleInFormation()

            Handler().postDelayed({
                runOnUiThread {
                    dismissFirstMoment()
                    showSecondMoment()
                }
            }, 3000)
        }
    }

    private fun dismissFirstMoment() {
        rlFirstMoment.animate().alpha(0f)
    }

    private fun showSecondMoment() {
        rlSecondMoment.animate().alpha(1f)
        moveToCenter()
    }

    private fun moveToCenter() {

        val dm = DisplayMetrics()
        // this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        windowManager.defaultDisplay.getMetrics(dm)
        val statusBarOffset = dm.heightPixels - root.measuredHeight

        val originalPos = IntArray(2)
        rlSecondMoment.getLocationOnScreen(originalPos)

        var xDest = dm.widthPixels / 2
        xDest -= vReferencia.measuredWidth / 2
        val yDest = dm.heightPixels / 2 - (vReferencia.measuredHeight / 2 - statusBarOffset)

        val anim = TranslateAnimation(0f, (xDest - originalPos[0]).toFloat(), 0f, (yDest - originalPos[1]).toFloat())
        anim.duration = 1000
        anim.fillAfter = true
        rlSecondMoment.startAnimation(anim)
        (ivSparkle.background as AnimationDrawable).start()
    }


    private fun playSparkleInFormation() {
        (ivSparkle1.background as AnimationDrawable).start()
    }

    /*
    private fun playVideo() {
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.keepScreenOn = true
        videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.formacao)
        videoView.start()
        videoView.requestFocus()
    }

    */

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
