package com.az.channel

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.az.channel.adapter.ChannelListNewAdapter
import com.az.channel.m3u.ChannelItem
import com.az.channel.m3u.ChannelList
import com.az.channel.model.BackgroundImage
import com.az.channel.model.ChannelItemNew
import com.az.channel.model.WeatherInfo
import com.az.channel.utils.Endpoints
import com.az.channel.utils.ServiceBuilder
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import android.R.attr.data


class MainActivity : AppCompatActivity() {

    var index = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getBackgroundImage()
        getListOfChannel()
        getWeatherInformation()
        recyclerView.isFocusableInTouchMode = true;
        index = 0;
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        var handled = false
        when (keyCode) {

            KeyEvent.KEYCODE_DPAD_LEFT ->         // ... handle left action
            {
                index--
                try {
                    recyclerView.getChildAt(index).requestFocus()
                } catch (ex: Exception) {
                    index++
                }
                handled = true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                index++
                try {
                    recyclerView.getChildAt(index).requestFocus()
                } catch (ex: Exception) {
                    index--
                }
                handled = true
            }
        }
        return handled || super.onKeyDown(keyCode, event)
    }

    private fun playTv(url: String) {
        val new = mapOf<String, String>("test" to "s")
        val cl = ChannelList()
        val ci = ChannelItem(
            name = "test", url = url,
            metadata = new
        )
        cl.add(ci)
        val intent = Intent(this, FullScreenVideoPlayerActivity::class.java)
        val gson = Gson()
        val myJson = gson.toJson(cl.items)
        intent.putExtra("EXTRA_LIST", myJson)
        intent.putExtra("EXTRA_INDEX", 0)
        startActivity(intent)
    }

    private fun getBackgroundImage() {
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        val call = request.getBackgroundImage()
        call.enqueue(object : retrofit2.Callback<BackgroundImage> {
            override fun onResponse(
                call: retrofit2.Call<BackgroundImage>,
                response: retrofit2.Response<BackgroundImage>
            ) {
                if (response.isSuccessful) {
                    Glide.with(this@MainActivity).load(response.body()?.imageUrl).into(imageView2)
                }
            }

            override fun onFailure(call: retrofit2.Call<BackgroundImage>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getListOfChannel() {
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        val call = request.getChannelList()
        call.enqueue(object : retrofit2.Callback<List<ChannelItemNew>> {
            override fun onResponse(
                call: retrofit2.Call<List<ChannelItemNew>>,
                response: retrofit2.Response<List<ChannelItemNew>>
            ) {
                if (response.isSuccessful) {
                    val newAdapter = ChannelListNewAdapter(response.body()!!)
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        );
                        adapter = newAdapter
                    }
                    newAdapter.onItemClick = { channel ->
                        playTv(channel.streamUrl)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<ChannelItemNew>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getWeatherInformation() {
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        val call = request.getWeatherInformation()
        call.enqueue(object : retrofit2.Callback<WeatherInfo> {
            override fun onResponse(
                call: retrofit2.Call<WeatherInfo>,
                response: retrofit2.Response<WeatherInfo>
            ) {
                if (response.isSuccessful) {
                    weather_temperature.text = response.body()!!.temperature
                }
            }

            override fun onFailure(call: retrofit2.Call<WeatherInfo>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }


}
