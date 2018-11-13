package br.com.rodriguesalex.cast

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.MotionEvent
import android.R.attr.y
import android.support.v4.view.ViewCompat.setY
import android.R.attr.x
import android.support.v4.view.ViewCompat.setX
import android.graphics.PointF
import android.view.View.OnTouchListener
import android.widget.FrameLayout


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {


    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener { view ->
            playFormation()
            Handler().postDelayed({
                runOnUiThread {
                    hideFirstMoment()
                }
            }, 2800)
            Handler().postDelayed({
                runOnUiThread {
                    showSecondMoment()
                }
            }, 3000)
            Handler().postDelayed({
                runOnUiThread {
                    startDisneyParticleIntro()
                }
            }, 6000)

        }
    }

    private fun hideFirstMoment() {
        ivFormation.visibility = View.INVISIBLE
    }

    private fun boucing(target: RelativeLayout) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.boucing)
        animation.repeatCount = Animation.INFINITE
        target.startAnimation(animation)
    }

    private fun showSecondMoment() {
        rlSecondMoment.animate().alpha(1f)
        moveToCenter()
    }

    private fun moveToCenter() {

        val dm = DisplayMetrics()
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

        showEtherBackground()
        playSparkling()
    }

    private fun showEtherBackground() {
        ivEtherBackground.animate().alpha(1f).setDuration(2000)
        ivStars.animate().alpha(1f).setDuration(2000)
        Glide.with(this).load(R.drawable.starts).into(ivStars)

    }

    private fun playFormation() {
        Glide.with(this).load(R.drawable.formation).into(ivFormation)
    }

    private fun playSparkling() {
        (ivSparkle.background as AnimationDrawable).start()
    }

    private fun startDisneyParticleIntro() {
        Handler().postDelayed({
            runOnUiThread {
                sparklesLoop()
            }
        }, 1800)
        Glide.with(this).load(R.drawable.sparkles).into(ivSparkingIntro)
    }

    private fun sparklesLoop() {
        ivSparkingIntro.visibility = View.GONE
        ivSparkleLoop.alpha = 1.0f
        (ivSparkleLoop.background as AnimationDrawable).start()
        wobble()
        flContainerCoverSecundary.setOnClickListener {
            launchUp()
        }
    }

    private fun wobble() {
        val wobbling = AnimationUtils.loadAnimation(this, R.anim.wobble)
        ivCoverSecundary.startAnimation(wobbling)
        ivSparkle.startAnimation(wobbling)
    }

    private fun launchUp() {
        flContainerCoverSecundary.animate().y(-1500f).setDuration(1000)
        launchSmoke()
        Handler().postDelayed({
            runOnUiThread {
                ivSparkleLaunchSmoke.animate().alpha(0f).setDuration(1000)
            }
        }, 1000)
    }

    private fun launchSmoke() {
        ivSparkleLaunchSmoke.animate().alpha(1f).setDuration(2000)
        (ivSparkleLaunchSmoke.background as AnimationDrawable).start()
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
