package com.vbstudio.covid19.home.dao

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryTestingDailyData(

	@SerializedName("testsperconfirmedcase")
	val testsperconfirmedcase: String? = null,

	@SerializedName("individualstestedperconfirmedcase")
	val individualstestedperconfirmedcase: String? = null,

	@SerializedName("testpositivityrate")
	val testpositivityrate: String? = null,

	@SerializedName("testsconductedbyprivatelabs")
	val testsconductedbyprivatelabs: String? = null,

	@SerializedName("testspermillion")
	val testspermillion: String? = null,

	@SerializedName("totalsamplestested")
	val totalsamplestested: String? = null,

	@SerializedName("positivecasesfromsamplesreported")
	val positivecasesfromsamplesreported: String? = null,

	@SerializedName("samplereportedtoday")
	val samplereportedtoday: String? = null,

	@SerializedName("source")
	val source: String? = null,

	@SerializedName("updatetimestamp")
	val updatetimestamp: String? = null,

	@SerializedName("totalindividualstested")
	val totalindividualstested: String? = null,

	@SerializedName("totalpositivecases")
	val totalpositivecases: String? = null
) : Parcelable
