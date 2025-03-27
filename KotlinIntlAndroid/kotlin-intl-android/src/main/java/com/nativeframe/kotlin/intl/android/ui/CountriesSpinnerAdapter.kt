package com.nativeframe.kotlin.intl.android.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.nativeframe.kotlin.intl.android.Country
import com.nativeframe.kotlin.intl.android.R

/**
 * Adapter for the countries list spinner
 *
 * @author Ismail Almetwally created on 5/6/16 (java).
 * @author Antonio Johnson modified 03/26/24 (kotlin).
 *
 * @param context Context
 * @param countries Countries
 * @param showCountryName Whether to show the country name
 * @param showCountryCode Whether to show the country code
 */
class CountriesSpinnerAdapter(
    context: Context,
    countries: Array<Country>,
    private val showCountryName: Boolean = true,
    private val showCountryCode: Boolean = true
) : ArrayAdapter<Country>(context, R.layout.kia_view_country, R.id.name, countries),
    SpinnerAdapter {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cView = convertView
        val country = getItem(position)!!
        if (cView == null) {
            cView = inflater.inflate(R.layout.kia_view_spinner, parent, false)
        }
        val imageView = cView!!.findViewById<ImageView>(R.id.flag)
        imageView.setImageResource(country.drawable)
        imageView.contentDescription = country.name
        return cView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cView: View? = convertView
        val viewHolder: ViewHolder
        if (cView == null) {
            cView = inflater.inflate(R.layout.kia_view_country, parent, false)
            viewHolder = ViewHolder().apply {
                name = cView!!.findViewById(R.id.name)
                dialCode = cView.findViewById(R.id.dial_code)
                flag = cView.findViewById(R.id.flag)
                cView.tag = this
            }
        } else {
            viewHolder = cView.tag as ViewHolder
        }
        val country = getItem(position)!!
        viewHolder.flag!!.setImageResource(country.drawable)
        viewHolder.name?.let {
            it.text = country.displayName
            it.visibility = if (showCountryName) View.VISIBLE else View.GONE
        }
        viewHolder.dialCode?.let {
            it.text = country.dialCode.toString()
            it.visibility = if (showCountryCode) View.VISIBLE else View.GONE
        }

        return cView!!
    }

    private class ViewHolder {
        var name: TextView? = null
        var dialCode: TextView? = null
        var flag: ImageView? = null
    }
}