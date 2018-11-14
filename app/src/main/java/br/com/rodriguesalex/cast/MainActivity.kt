package br.com.rodriguesalex.cast

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.SeekBar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.R.attr.radius
import android.media.MediaPlayer
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions




class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {


    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
    }

    private var currentMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        roundImageView()

        ivCoverPrimary.setOnClickListener {
            playSound(R.raw.intro, false)
            playFormation()
            Handler().postDelayed({
                runOnUiThread {
                    hideFirstMoment()
                }
            }, Constants.HideFirstMomentDelay)
            Handler().postDelayed({
                runOnUiThread {

                    showSecondMoment()
                }
            }, Constants.ShowSecondMomentDelay)
            Handler().postDelayed({
                runOnUiThread {
                    playSound(R.raw.sparkles, true)
                    showArrow()
                    sparklesLoop()
                }
            }, Constants.ShowSparklesLoopDelay)

        }

    }

    private fun hideFirstMoment() {
        rlSecondMoment.animate().alpha(1f)
    }

    private fun showSecondMoment() {
        ivFormation.visibility = View.GONE
        ivCoverPrimary.visibility = View.GONE
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
        anim.duration = Constants.MoveToCenterDuration
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

    private fun roundImageView() {
        val options = RequestOptions().transforms(RoundedCorners(8))
        Glide.with(this).applyDefaultRequestOptions(options).load(R.drawable.cover).into(ivCoverPrimary)
        Glide.with(this).applyDefaultRequestOptions(options).load(R.drawable.cover).into(ivCoverSecundary)
    }

    private fun playFormation() {
        ivFormation.animate().alpha(1f).setDuration(500)
        (ivFormation.background as AnimationDrawable).start()
    }

    private fun playSparkling() {
        (ivSparkle.background as AnimationDrawable).start()
    }

    private fun showArrow() {
        ivArrow.animate().alpha(1f).setDuration(200)
        val anim = AnimationUtils.loadAnimation(this, R.anim.up_down)
        ivArrow.startAnimation(anim)
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
        ivSparkleLaunchSmoke.animate().alpha(0f).setDuration(1000)
        launchSmoke()
        Handler().postDelayed({
            runOnUiThread {
                flContainerCoverSecundary.animate().y(-1500f)
            }
        }, Constants.ShowLaunchSmokeDelay)
    }

    private fun launchSmoke() {
        playSound(R.raw.launch, false)
        ivSparkleLoop.animate().alpha(0f)
        ivSparkleLaunchSmoke.animate().alpha(1f)
        (ivSparkleLaunchSmoke.background as AnimationDrawable).start()
    }

    private fun playSound(resId: Int, isLoop: Boolean) {
        currentMediaPlayer?.stop()
        currentMediaPlayer = MediaPlayer.create(this, resId)
        currentMediaPlayer?.isLooping = isLoop
        currentMediaPlayer?.setOnCompletionListener {
            it.isLooping = isLoop
        }
        currentMediaPlayer?.setVolume(1000f, 1000f)
        currentMediaPlayer?.start()
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
