package com.example.slotmachine

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.core.graphics.toColorInt

class MainActivity : AppCompatActivity() {
    private lateinit var shared : SharedPreferences
    private var doneChecked = 0
    private var coins: Int = 0
    private var planeFrames: Int = 0
    private var spinning1 = false
    private var spinning2 = false
    private var spinning3 = false
    private var spinning = false
    private var speed1 = 0.5
    private var speed2 = 0.5
    private var speed3 = 0.5
    private var rotating1 = false
    private var rotating2 = false
    private var rotating3 = false
    private lateinit var balance: TextView
    private lateinit var prizeBtn: ImageButton
    private lateinit var prizeList: ScrollView
    private lateinit var boeing747: ImageView
    private lateinit var spinBtn: Button
    private lateinit var num1: TextView
    private lateinit var num2: TextView
    private lateinit var num3: TextView
    private lateinit var frame1: FrameLayout
    private lateinit var frame2: FrameLayout
    private lateinit var frame3: FrameLayout
    private var numb1: Int = (1..7).random()
    private var numb2: Int = (1..7).random()
    private var numb3: Int = (1..7).random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balance = findViewById(R.id.balanceText)
        prizeList = findViewById(R.id.prizeList)
        prizeBtn = findViewById(R.id.button2)
        boeing747 = findViewById(R.id.plane747)
        spinBtn = findViewById(R.id.button)
        num1 = findViewById(R.id.num1)
        num2 = findViewById(R.id.num2)
        num3 = findViewById(R.id.num3)
        frame1 = findViewById(R.id.frame1)
        frame2 = findViewById(R.id.frame2)
        frame3 = findViewById(R.id.frame3)
        numb3 = (3..7).random()
        numb1 = (3..7).random()
        numb2 = (3..7).random()
        num1.text = getString(R.string.lucky_numb1, numb1.toString())
        num2.text = getString(R.string.lucky_numb2, numb2.toString())
        num3.text = getString(R.string.lucky_numb3, numb3.toString())
        balance.text = getString(R.string.coins, coins.toString())

        shared = getSharedPreferences("CodeMinigameDB", Context.MODE_PRIVATE)

        readData()

        balance.text = getString(R.string.coins, coins.toString())

        balance.setOnClickListener {
            rotateBal()
        }

        when (num1.text) {
            "1" -> num1.setTextColor(getColor(R.color.one))
            "2" -> num1.setTextColor(getColor(R.color.two))
            "3" -> num1.setTextColor(getColor(R.color.three))
            "4" -> num1.setTextColor(getColor(R.color.four))
            "5" -> num1.setTextColor(getColor(R.color.five))
            "6" -> num1.setTextColor(getColor(R.color.six))
            "7" -> num1.setTextColor(getColor(R.color.seven))
            "8" -> num1.setTextColor(getColor(R.color.eight))
            "9" -> num1.setTextColor(getColor(R.color.nine))
            else -> {
                num1.setTextColor(getColor(R.color.teal_700))
                println("number 1's number is not between 1 and 9")
            }
        }

        when (num2.text) {
            "1" -> num2.setTextColor(getColor(R.color.one))
            "2" -> num2.setTextColor(getColor(R.color.two))
            "3" -> num2.setTextColor(getColor(R.color.three))
            "4" -> num2.setTextColor(getColor(R.color.four))
            "5" -> num2.setTextColor(getColor(R.color.five))
            "6" -> num2.setTextColor(getColor(R.color.six))
            "7" -> num2.setTextColor(getColor(R.color.seven))
            "8" -> num2.setTextColor(getColor(R.color.eight))
            "9" -> num2.setTextColor(getColor(R.color.nine))
            else -> {
                num2.setTextColor(getColor(R.color.teal_700))
                println("number 2's number is not between 1 and 9")
            }
        }


        when (num3.text) {
            "1" -> num3.setTextColor(getColor(R.color.one))
            "2" -> num3.setTextColor(getColor(R.color.two))
            "3" -> num3.setTextColor(getColor(R.color.three))
            "4" -> num3.setTextColor(getColor(R.color.four))
            "5" -> num3.setTextColor(getColor(R.color.five))
            "6" -> num3.setTextColor(getColor(R.color.six))
            "7" -> num3.setTextColor(getColor(R.color.seven))
            "8" -> num3.setTextColor(getColor(R.color.eight))
            "9" -> num3.setTextColor(getColor(R.color.nine))
            else -> {
                num3.setTextColor(getColor(R.color.teal_700))
                println("number 3's number is not between 1 and 9")
            }
        }

        frame1.setOnClickListener {
            if(!spinning1) {
                rotating1 = true
                rotate1(25)
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                                    rotating1 = false
                }, (100).toLong())
            }
        }

        frame2.setOnClickListener {
            if(!spinning2) {
                rotating2 = true
                rotate2(25)
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    rotating2 = false
                }, (100).toLong())
            }
        }

        frame3.setOnClickListener {
            if(!spinning3) {
                rotating3 = true
                rotate3(25)
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    rotating3 = false
                }, (100).toLong())
            }
        }
    }

    private fun rotateBal() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if(balance.rotationX in 0f..50f) balance.rotationX += 5f
            else if(balance.rotationX in 310f..359f) balance.rotationX += 5f
                else if (balance.rotationX < 330f) balance.rotationX += 10f
            if(balance.rotationX >= 360f) {
                balance.rotationX = 0f
            } else rotateBal()
        }, (1).toLong())
    }

    private fun insaneRotate2(rotSpeed: Int) {
        var speed = rotSpeed
        rainbowEffect(spinBtn)
        when((1..32).random()) {
            1 -> {
                if (speed > 15) {
                    speed *= speed
                } else speed++
            }
            2 -> speed++
//            27 -> when((1..3).random()) {
//                1 -> frame2.scaleX += 0.01f
//                3 -> frame2.scaleX -= 0.01f
//                2 -> when((1..5).random()) {
//                    4 -> frame2.scaleX *= -1
//                }
//            }
        }
        when((1..4).random()) {
            in 1..3 -> {
                if(speed >= 21) spinBtn.scaleY += speed * 0.001f
            }
        }
        spinBtn.rotationX += speed
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({insaneRotate2(speed)
        }, (40).toLong())
    }

    private fun rotate1(rotSpeed: Int) {
        doneChecked = 0
        spinning1 = true
        val handler = Handler(Looper.getMainLooper())
        if (frame1.rotationX >= 90f) {
            frame1.rotationX = -90f
            numb1++
            if (numb1 >= 8) numb1 = 1
            num1.text = numb1.toString()
            when (num1.text) {
                "1" -> num1.setTextColor(getColor(R.color.one))
                "2" -> num1.setTextColor(getColor(R.color.two))
                "3" -> num1.setTextColor(getColor(R.color.three))
                "4" -> num1.setTextColor(getColor(R.color.four))
                "5" -> num1.setTextColor(getColor(R.color.five))
                "6" -> num1.setTextColor(getColor(R.color.six))
                "7" -> num1.setTextColor(getColor(R.color.seven))
                "8" -> num1.setTextColor(getColor(R.color.eight))
                "9" -> num1.setTextColor(getColor(R.color.nine))
                else -> {
                    num1.setTextColor(getColor(R.color.teal_700))
                    println("number 1's number is not between 1 and 9")
                }
            }
        }
        handler.postDelayed({
            frame1.rotationX += speed1.toFloat()
            if(rotating1) {
                if (speed1 < rotSpeed) {
                    speed1 += 5
                }
                rotate1(rotSpeed)
            }
            else if (!rotating1 && frame1.rotationX != 0f) {
                if(speed1 > 3) speed1 -= 0.5
                rotate1(rotSpeed)
            } else if (!rotating1 && frame1.rotationX == 0f && speed1 > 8) rotate1(rotSpeed)
            handler.postDelayed({
                if(!rotating1 && frame1.rotationX == 0f && speed1 <= 8) {
                    spinning1 = false
                    checkIfDone()
                }
            }, (100).toLong())
        }, (50).toLong())
    }

    private fun rotate2(rotSpeed: Int) {
        doneChecked = 0
        spinning2 = true
        val handler = Handler(Looper.getMainLooper())
        if (frame2.rotationX >= 90f) {
            frame2.rotationX = -90f
            numb2++
            if (numb2 >= 8) numb2 = 1
            num2.text = numb2.toString()
            when (num2.text) {
                "1" -> num2.setTextColor(getColor(R.color.one))
                "2" -> num2.setTextColor(getColor(R.color.two))
                "3" -> num2.setTextColor(getColor(R.color.three))
                "4" -> num2.setTextColor(getColor(R.color.four))
                "5" -> num2.setTextColor(getColor(R.color.five))
                "6" -> num2.setTextColor(getColor(R.color.six))
                "7" -> num2.setTextColor(getColor(R.color.seven))
                "8" -> num2.setTextColor(getColor(R.color.eight))
                "9" -> num2.setTextColor(getColor(R.color.nine))
                else -> {
                    num2.setTextColor(getColor(R.color.teal_700))
                    println("number 2's number is not between 1 and 9")
                }
            }
        }
        handler.postDelayed({
            frame2.rotationX += speed2.toFloat()
            if(rotating2) {
                if (speed2 < rotSpeed) {
                    speed2 += 5
                }
                rotate2(rotSpeed)
            }
            else if (!rotating2 && frame2.rotationX != 0f) {
                if(speed2 > 3) speed2 -= 0.5
                rotate2(rotSpeed)
            } else if (!rotating2 && frame2.rotationX == 0f && speed2 > 8) rotate2(rotSpeed)
            handler.postDelayed({
                if(!rotating2 && frame2.rotationX == 0f && speed2 <= 8) {
                    spinning2 = false
                    checkIfDone()
                }
            }, (100).toLong())
        }, (50).toLong())
    }

    private fun rotate3(rotSpeed: Int) {
        doneChecked = 0
        spinning3 = true
        val handler = Handler(Looper.getMainLooper())
        if (frame3.rotationX >= 90f) {
            frame3.rotationX = -90f
            numb3 ++
            if (numb3 >= 8) numb3 = 1
            num3.text = numb3.toString()
            when (num3.text) {
                "1" -> num3.setTextColor(getColor(R.color.one))
                "2" -> num3.setTextColor(getColor(R.color.two))
                "3" -> num3.setTextColor(getColor(R.color.three))
                "4" -> num3.setTextColor(getColor(R.color.four))
                "5" -> num3.setTextColor(getColor(R.color.five))
                "6" -> num3.setTextColor(getColor(R.color.six))
                "7" -> num3.setTextColor(getColor(R.color.seven))
                "8" -> num3.setTextColor(getColor(R.color.eight))
                "9" -> num3.setTextColor(getColor(R.color.nine))
                else -> {
                    num3.setTextColor(getColor(R.color.teal_700))
                    println("number 3's number is not between 1 and 9")
                }
            }
        }
        handler.postDelayed({
            frame3.rotationX += speed3.toFloat()
            if(rotating3) {
                if (speed3 < rotSpeed) {
                    speed3 += 5
                }
                rotate3(rotSpeed)
            }
            else if (!rotating3 && frame3.rotationX != 0f) {
                if(speed3 > 3) speed3 -= 0.5
                rotate3(rotSpeed)
            } else if (!rotating3 && frame3.rotationX == 0f && speed3 > 8) rotate3(rotSpeed)
            handler.postDelayed({
                if(!rotating3 && frame3.rotationX == 0f && speed3 <= 8) {
                    spinning3 = false
                    checkIfDone()
                }
            }, (100).toLong())
        }, (50).toLong())
    }

    private fun checkIfDone() {

        if (!spinning1 && !spinning2 && !spinning3 && doneChecked == 0) {
            val handler = Handler(Looper.getMainLooper())
            spinning = false
            doneChecked ++
            if (num1.text == "7" && num2.text == "4" && num3.text == "7") {
                println("Never Gonna Give You A 747.")
                planeFrames = 0
                boeing747.translationY = -15f
                boeing747.scaleX = 0.75f
                boeing747.scaleY = 0.75f
                b747pass()

                Toast.makeText(applicationContext, "Watch out for the Boeing 747 airliner", Toast.LENGTH_SHORT).show()
                coins += 74700
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "3" && num2.text == "5" && num3.text == "3") {
                println("The song \"Never Gonna Give You Up\" by Rick Astley contains 353 words.")
                coins += 500
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
                handler.postDelayed({
                    Toast.makeText(applicationContext, "+500 Coins", Toast.LENGTH_SHORT).show()
                }, (2500).toLong())
                val url = Uri.parse("https://www.youtube.com/watch?v=pyoURHR-g4U")
                val intent = Intent(Intent.ACTION_VIEW, url)
                startActivity(intent)
                Toast.makeText(applicationContext, "Never Gonna Give You Up", Toast.LENGTH_SHORT).show()

            }
            if (num1.text == "4" && num2.text == "5" && num3.text == "6") {
                println("456!")
                Toast.makeText(applicationContext, "◯⬚△", Toast.LENGTH_SHORT).show()
                coins += 4560
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "1" && num2.text == "2" && num3.text == "3") {
                println("123!")
                Toast.makeText(applicationContext, "AA1AAA2AAAA4AAAA7AAAAAÆ! +12345 Coins", Toast.LENGTH_SHORT).show()
                coins += 12345
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "1" && num2.text == "1" && num3.text == "1") {
                println("111!")
                Toast.makeText(applicationContext, "Winner! +100 Coins", Toast.LENGTH_SHORT).show()
                coins += 100
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "2" && num2.text == "2" && num3.text == "2") {
                println("222!")
                Toast.makeText(applicationContext, "Winner! +250 Coins", Toast.LENGTH_SHORT).show()
                coins += 250
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "3" && num2.text == "3" && num3.text == "3") {
                println("333!")
                Toast.makeText(applicationContext, "Winner! +500 Coins", Toast.LENGTH_SHORT).show()
                coins += 500
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "4" && num2.text == "4" && num3.text == "4") {
                println("444!")
                Toast.makeText(applicationContext, "Winner! +1000 Coins", Toast.LENGTH_SHORT).show()
                coins += 1000
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "5" && num2.text == "5" && num3.text == "5") {
                println("555!")
                Toast.makeText(applicationContext, "Winner! +2000 Coins", Toast.LENGTH_SHORT).show()
                coins += 2000
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            if (num1.text == "6" && num2.text == "6" && num3.text == "6") {
                println("AMඞGUS")
                Toast.makeText(applicationContext, "YOU HAVE BEEN CURSED BY THE UNHOLY AMඞGUS\n-60,000 Coins!", Toast.LENGTH_SHORT).show()
                coins -= 60000
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
                insaneRotate2(5)
            }
            if (num1.text == "7" && num2.text == "7" && num3.text == "7") {
                println("777JACKPOT!")
                Toast.makeText(applicationContext, "LUCKY 7 JACKPOT! +500,000 COINS!", Toast.LENGTH_SHORT).show()
                coins += 500000
                rotateBal()
                balance.text = getString(R.string.coins, coins.toString())
            }
            saveStats()
        }
    }

    private fun b747pass() {

        if (planeFrames <= 175) {
            val handler = Handler(Looper.getMainLooper())
            planeFrames++
            handler.postDelayed({
                boeing747.translationY += 25
                boeing747.scaleX += 0.02f
                boeing747.scaleY += 0.02f
                b747pass()
            }, (25).toLong())
        } else if (planeFrames <= 225) {
            val handler = Handler(Looper.getMainLooper())
            planeFrames++
            handler.postDelayed({
                boeing747.translationY += 15
                boeing747.scaleX += 0.030f
                boeing747.scaleY += 0.033f
                b747pass()
            }, (25).toLong())
        } else if (planeFrames <= 250) {
            val handler = Handler(Looper.getMainLooper())
            planeFrames++
            handler.postDelayed({
                boeing747.translationY += 5
                boeing747.scaleX += 0.045f
                boeing747.scaleY += 0.045f
                b747pass()
            }, (25).toLong())
        }
        else if(planeFrames > 251) {
            planeFrames = 0
            boeing747.translationY = -15f
            boeing747.scaleX = 0.75f
            boeing747.scaleY = 0.75f
        }
    }


    fun spin(view: View) {
        checkIfDone()
        if (!spinning) {
            doneChecked = 0
            val handler = Handler(Looper.getMainLooper())
            spinning = true
            speed1 = 0.5
            speed2 = 0.5
            speed3 = 0.5
            rotating1 = true
            rotating2 = true
            rotating3 = true
            rainbowEffect(spinBtn)

            rotate1(100)
            handler.postDelayed({
                rotating1 = false
            }, ((3400..7654).random()).toLong())
            handler.postDelayed({
                rotate2(50)
                handler.postDelayed({
                    rotating2 = false
                }, ((3500..7755).random()).toLong())
                handler.postDelayed({
                    rotate3(10)
                    handler.postDelayed({
                        rotating3 = false
                    }, ((4100..7855).random()).toLong())
                }, (100).toLong())
            }, (140).toLong())
        }
    }

    private fun rainbowEffect(theScreen: Button) {

        val rainbow0 = "#FF0000"
        val rainbow5 = "#ff1500"
        val rainbow10 = "#ff2a00"
        val rainbow15 = "#ff4000"
        val rainbow20 = "#ff5500"
        val rainbow25 = "#ff6a00"
        val rainbow30 = "#ff8000"
        val rainbow35 = "#ff9500"
        val rainbow40 = "#ffaa00"
        val rainbow45 = "#ffbf00"
        val rainbow50 = "#ffd500"// Fiftey
        val rainbow55 = "#ffea00"
        val rainbow60 = "#ffff00"
        val rainbow65 = "#eaff00"
        val rainbow70 = "#d4ff00"
        val rainbow75 = "#bfff00"
        val rainbow80 = "#aaff00"
        val rainbow85 = "#95ff00"
        val rainbow90 = "#80ff00"
        val rainbow95 = "#6aff00"
        val rainbow100 = "#55ff00"
        val rainbow105 = "#40ff00"
        val rainbow110 = "#2bff00"
        val rainbow115 = "#15ff00"
        val rainbow120 = "#00ff00"
        val rainbow125 = "#00ff15"
        val rainbow130 = "#00ff2a"
        val rainbow135 = "#00ff40"
        val rainbow140 = "#00ff55"
        val rainbow145 = "#00ff6a"
        val rainbow150 = "#00ff80"
        val rainbow155 = "#00ff95"
        val rainbow160 = "#00ffaa"
        val rainbow165 = "#00ffbf"
        val rainbow170 = "#00ffd5"
        val rainbow175 = "#00ffea"
        val rainbow180 = "#00ffff"
        val rainbow185 = "#00eaff"
        val rainbow190 = "#00d4ff"
        val rainbow195 = "#00bfff"
        val rainbow200 = "#00aaff"
        val rainbow205 = "#0095ff"
        val rainbow210 = "#0080ff"
        val rainbow215 = "#006aff"
        val rainbow220 = "#0055ff"
        val rainbow225 = "#0040ff"
        val rainbow230 = "#002aff"
        val rainbow235 = "#0015ff"
        val rainbow240 = "#0000ff"
        val rainbow245 = "#1500ff"
        val rainbow250 = "#2b00ff"//no crying until 2:50
        val rainbow255 = "#4000ff"
        val rainbow260 = "#5500ff"
        val rainbow265 = "#6a00ff"
        val rainbow270 = "#8000ff"
        val rainbow275 = "#9500ff"
        val rainbow280 = "#aa00ff"
        val rainbow285 = "#bf00ff"
        val rainbow290 = "#d400ff"
        val rainbow295 = "#ea00ff"
        val rainbow300 = "#ff00ff"
        val rainbow305 = "#ff00ea"
        val rainbow310 = "#ff00d4"
        val rainbow315 = "#ff00bf"
        val rainbow320 = "#ff00aa"
        val rainbow325 = "#ff0095"
        val rainbow330 = "#ff0080"
        val rainbow335 = "#ff006a"
        val rainbow340 = "#ff0055"
        val rainbow345 = "#ff0040"
        val rainbow350 = "#ff002b"
        val rainbow355 = "#ff0015"

        theScreen.setBackgroundColor(rainbow0.toColorInt())

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            theScreen.setBackgroundColor(rainbow5.toColorInt())
            handler.postDelayed({
                theScreen.setBackgroundColor(rainbow10.toColorInt())
                handler.postDelayed({
                    theScreen.setBackgroundColor(rainbow15.toColorInt())
                    handler.postDelayed({
                        theScreen.setBackgroundColor(rainbow15.toColorInt())
                        handler.postDelayed({
                            theScreen.setBackgroundColor(rainbow20.toColorInt())
                            handler.postDelayed({
                                theScreen.setBackgroundColor(rainbow25.toColorInt())
                                handler.postDelayed({
                                    theScreen.setBackgroundColor(rainbow30.toColorInt())
                                    handler.postDelayed({
                                        theScreen.setBackgroundColor(rainbow35.toColorInt())
                                        handler.postDelayed({
                                            theScreen.setBackgroundColor(rainbow40.toColorInt())
                                            handler.postDelayed({
                                                theScreen.setBackgroundColor(rainbow45.toColorInt())
                                                handler.postDelayed({
                                                    theScreen.setBackgroundColor(rainbow50.toColorInt())
                                                    handler.postDelayed({
                                                        theScreen.setBackgroundColor(rainbow55.toColorInt())
                                                        handler.postDelayed({
                                                            theScreen.setBackgroundColor(rainbow60.toColorInt())
                                                            handler.postDelayed({
                                                                theScreen.setBackgroundColor(rainbow65.toColorInt())
                                                                handler.postDelayed({
                                                                    theScreen.setBackgroundColor(rainbow70.toColorInt())
                                                                    handler.postDelayed({
                                                                        theScreen.setBackgroundColor(rainbow75.toColorInt())
                                                                        handler.postDelayed({
                                                                            theScreen.setBackgroundColor(rainbow80.toColorInt())
                                                                            handler.postDelayed({
                                                                                theScreen.setBackgroundColor(rainbow85.toColorInt())
                                                                                handler.postDelayed({
                                                                                    theScreen.setBackgroundColor(rainbow90.toColorInt())
                                                                                    handler.postDelayed({
                                                                                        theScreen.setBackgroundColor(rainbow95.toColorInt())
                                                                                        handler.postDelayed({
                                                                                            theScreen.setBackgroundColor(rainbow100.toColorInt())
                                                                                            handler.postDelayed({
                                                                                                theScreen.setBackgroundColor(rainbow105.toColorInt())
                                                                                                handler.postDelayed({
                                                                                                    theScreen.setBackgroundColor(rainbow110.toColorInt())
                                                                                                    handler.postDelayed({
                                                                                                        theScreen.setBackgroundColor(rainbow115.toColorInt())
                                                                                                        handler.postDelayed({
                                                                                                            theScreen.setBackgroundColor(rainbow120.toColorInt())
                                                                                                            handler.postDelayed({
                                                                                                                theScreen.setBackgroundColor(rainbow125.toColorInt())
                                                                                                                handler.postDelayed({
                                                                                                                    theScreen.setBackgroundColor(rainbow130.toColorInt())
                                                                                                                    handler.postDelayed({
                                                                                                                        theScreen.setBackgroundColor(rainbow135.toColorInt())
                                                                                                                        handler.postDelayed({
                                                                                                                            theScreen.setBackgroundColor(rainbow140.toColorInt())
                                                                                                                            handler.postDelayed({
                                                                                                                                theScreen.setBackgroundColor(rainbow145.toColorInt())
                                                                                                                                handler.postDelayed({
                                                                                                                                    theScreen.setBackgroundColor(rainbow150.toColorInt())
                                                                                                                                    handler.postDelayed({
                                                                                                                                        theScreen.setBackgroundColor(rainbow155.toColorInt())
                                                                                                                                        handler.postDelayed({
                                                                                                                                            theScreen.setBackgroundColor(rainbow160.toColorInt())
                                                                                                                                            handler.postDelayed({
                                                                                                                                                theScreen.setBackgroundColor(rainbow165.toColorInt())
                                                                                                                                                handler.postDelayed({
                                                                                                                                                    theScreen.setBackgroundColor(rainbow170.toColorInt())
                                                                                                                                                    handler.postDelayed({
                                                                                                                                                        theScreen.setBackgroundColor(rainbow175.toColorInt())
                                                                                                                                                        handler.postDelayed({
                                                                                                                                                            theScreen.setBackgroundColor(rainbow180.toColorInt())
                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                theScreen.setBackgroundColor(rainbow185.toColorInt())
                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                    theScreen.setBackgroundColor(rainbow190.toColorInt())
                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                        theScreen.setBackgroundColor(rainbow195.toColorInt())
                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                            theScreen.setBackgroundColor(rainbow200.toColorInt())
                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                theScreen.setBackgroundColor(rainbow205.toColorInt())
                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow210.toColorInt())
                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow215.toColorInt())
                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow220.toColorInt())
                                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                                theScreen.setBackgroundColor(rainbow225.toColorInt())
                                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow230.toColorInt())
                                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow235.toColorInt())
                                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow240.toColorInt())
                                                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                                                theScreen.setBackgroundColor(rainbow245.toColorInt())
                                                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow250.toColorInt())
                                                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow255.toColorInt())
                                                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow260.toColorInt())
                                                                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                                                                theScreen.setBackgroundColor(rainbow265.toColorInt())
                                                                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow270.toColorInt())
                                                                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow275.toColorInt())
                                                                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow280.toColorInt())
                                                                                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                                                                                theScreen.setBackgroundColor(rainbow285.toColorInt())
                                                                                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow290.toColorInt())
                                                                                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow295.toColorInt())
                                                                                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow300.toColorInt())
                                                                                                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                                                                                                theScreen.setBackgroundColor(rainbow305.toColorInt())
                                                                                                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow310.toColorInt())
                                                                                                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow315.toColorInt())
                                                                                                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow320.toColorInt())
                                                                                                                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                                                                                                                theScreen.setBackgroundColor(rainbow325.toColorInt())
                                                                                                                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow330.toColorInt())
                                                                                                                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow330.toColorInt())
                                                                                                                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow335.toColorInt())
                                                                                                                                                                                                                                                                                            handler.postDelayed({
                                                                                                                                                                                                                                                                                                theScreen.setBackgroundColor(rainbow340.toColorInt()) // K its 2:50 U can cry now.
                                                                                                                                                                                                                                                                                                handler.postDelayed({
                                                                                                                                                                                                                                                                                                    theScreen.setBackgroundColor(rainbow345.toColorInt())
                                                                                                                                                                                                                                                                                                    handler.postDelayed({
                                                                                                                                                                                                                                                                                                        theScreen.setBackgroundColor(rainbow350.toColorInt())
                                                                                                                                                                                                                                                                                                        handler.postDelayed({
                                                                                                                                                                                                                                                                                                            theScreen.setBackgroundColor(rainbow355.toColorInt())
                                                                                                                                                                                                                                                                                                            handler.postDelayed({
//                                                                                                                                                                                                                                                                                                                rainbowEffect(theScreen)
                                                                                                                                                                                                                                                                                                            }, 150)
                                                                                                                                                                                                                                                                                                        }, 150)
                                                                                                                                                                                                                                                                                                    }, 150)
                                                                                                                                                                                                                                                                                                }, 150)
                                                                                                                                                                                                                                                                                            }, 150)
                                                                                                                                                                                                                                                                                        }, 150)
                                                                                                                                                                                                                                                                                    }, 150)
                                                                                                                                                                                                                                                                                }, 150)
                                                                                                                                                                                                                                                                            }, 150)
                                                                                                                                                                                                                                                                        }, 150)
                                                                                                                                                                                                                                                                    }, 150)
                                                                                                                                                                                                                                                                }, 150)
                                                                                                                                                                                                                                                            }, 150)
                                                                                                                                                                                                                                                        }, 150)
                                                                                                                                                                                                                                                    }, 150)
                                                                                                                                                                                                                                                }, 150)
                                                                                                                                                                                                                                            }, 150)
                                                                                                                                                                                                                                        }, 150)
                                                                                                                                                                                                                                    }, 150)
                                                                                                                                                                                                                                }, 150)
                                                                                                                                                                                                                            }, 150)
                                                                                                                                                                                                                        }, 150)
                                                                                                                                                                                                                    }, 150)
                                                                                                                                                                                                                }, 150)
                                                                                                                                                                                                            }, 150)
                                                                                                                                                                                                        }, 150)
                                                                                                                                                                                                    }, 150)
                                                                                                                                                                                                }, 150)
                                                                                                                                                                                            }, 150)
                                                                                                                                                                                        }, 150)
                                                                                                                                                                                    }, 150)
                                                                                                                                                                                }, 150)
                                                                                                                                                                            }, 150)
                                                                                                                                                                        }, 150)
                                                                                                                                                                    }, 150)
                                                                                                                                                                }, 150)
                                                                                                                                                            }, 150)
                                                                                                                                                        }, 150)
                                                                                                                                                    }, 150)
                                                                                                                                                }, 150)
                                                                                                                                            }, 150)
                                                                                                                                        }, 150)
                                                                                                                                    }, 150)
                                                                                                                                }, 150)
                                                                                                                            }, 150)
                                                                                                                        }, 150)
                                                                                                                    }, 150)
                                                                                                                }, 150)
                                                                                                            }, 150)
                                                                                                        }, 150)
                                                                                                    }, 150)
                                                                                                }, 150)
                                                                                            }, 150)
                                                                                        }, 150)
                                                                                    }, 150)
                                                                                }, 150)
                                                                            }, 150)
                                                                        }, 150)
                                                                    }, 150)
                                                                }, 150)
                                                            }, 150)
                                                        }, 150)
                                                    }, 150)
                                                }, 150)
                                            }, 150)
                                        }, 150)
                                    }, 150)
                                }, 150)
                            }, 150)
                        }, 150)
                    }, 150)
                }, 150)
            }, 150)
        }, 150)
    }

    private fun readData() {
        coins = shared.getInt("coins", coins)
    }

    fun showRewards(view: View) {
        if (prizeList.visibility == View.GONE) prizeList.visibility = View.VISIBLE
        else if (prizeList.visibility == View.VISIBLE) prizeList.visibility = View.GONE
        println(prizeBtn.imageTintMode.toString())
        prizeBtn.imageTintMode = PorterDuff.Mode.DARKEN
        println(prizeBtn.imageTintMode.toString())
        println("yes")
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            prizeBtn.imageTintMode = PorterDuff.Mode.SCREEN
            println("EEEAEE")
        }, (150).toLong())

    }

    private fun saveStats() {
        val edit = shared.edit()
        edit.putInt("coins" , coins )
        edit.apply()
    }

}