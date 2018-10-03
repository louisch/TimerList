package xyz.louischan.timerlist

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.timer.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: TimersAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: MainActivityViewModel

    private val timer: java.util.Timer = java.util.Timer()

    private lateinit var handler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val timers = viewModel.addNewTimer()
            viewAdapter.updateTimers(timers)
        }

        handler = Handler(Handler.Callback { refreshTimers() })

        viewManager = LinearLayoutManager(this)
        viewAdapter = TimersAdapter(mutableListOf())

        recyclerView = findViewById<RecyclerView>(R.id.timers).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val task: TimerTask = object : TimerTask() {
            override fun run() {
                handler.sendEmptyMessage(0)
            }
        }

        timer.scheduleAtFixedRate(task, 0, 1000)
    }

    private fun refreshTimers() : Boolean {
        viewAdapter.notifyDataSetChanged()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
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

class TimersAdapter(var timers: List<Timer>) : RecyclerView.Adapter<TimersAdapter.ViewHolder>() {

    class ViewHolder(val timer: ConstraintLayout) : RecyclerView.ViewHolder(timer)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val timer = LayoutInflater.from(parent.context)
                .inflate(R.layout.timer, parent, false) as ConstraintLayout

        return ViewHolder(timer)
    }

    override fun getItemCount(): Int {
        return timers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.timer.timer_text.text = timers[position].runningTime(Calendar.getInstance().time)
    }

    fun updateTimers(timers: List<Timer>) {
        this.timers = timers
        notifyDataSetChanged()
    }
}