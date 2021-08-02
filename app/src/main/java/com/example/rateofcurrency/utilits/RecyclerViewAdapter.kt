package com.example.rateofcurrency.utilits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rateofcurrency.R
import com.example.rateofcurrency.models.entities.SignalResponse
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MainAdapter(var signalArray: ArrayList<SignalResponse>): RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view, parent, false)
        return MainViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val oneItem = signalArray[position]
        holder.bind(oneItem)
    }

    override fun getItemCount(): Int = signalArray.size

}


class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val tvId = itemView.findViewById<TextView>(R.id.tv_id_val)
    private val tvActTime = itemView.findViewById<TextView>(R.id.tv_act_time_val)
    private val tvComment = itemView.findViewById<TextView>(R.id.tv_comment_val)
    private val tvPair = itemView.findViewById<TextView>(R.id.tv_pair_val)
    private val tvCmd = itemView.findViewById<TextView>(R.id.tv_cmd_val)
    private val tvTradSys = itemView.findViewById<TextView>(R.id.tv_trad_sys_val)
    private val tvPeriod = itemView.findViewById<TextView>(R.id.tv_period_val)
    private val tvPrice = itemView.findViewById<TextView>(R.id.tv_price_val)
    private val tvSl = itemView.findViewById<TextView>(R.id.tv_sl_val)
    private val tvTp = itemView.findViewById<TextView>(R.id.tv_tp_val)

    fun bind(signalResp: SignalResponse) {
        tvId.text = signalResp.id.toString()
        tvActTime.text =
            Instant.fromEpochSeconds(signalResp.actualTime).toLocalDateTime(TimeZone.UTC).toString()
        tvComment.text = signalResp.comment
        tvPair.text = signalResp.pair
        tvCmd.text = signalResp.cmd.toString()
        tvTradSys.text = signalResp.tradingSystem.toString()
        tvPeriod.text = signalResp.period
        tvPrice.text = signalResp.price.toString()
        tvSl.text = signalResp.sl.toString()
        tvTp.text = signalResp.tp.toString()
    }
}