package com.plcoding.backgroundlocationtracking.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "app_preferences"
        private const val USER_UID = "user_id"
        private const val USER_NAME = "name"
        private const val USER_EMAIL = "email"
        private const val EMPLOYEE_ID = "employeeId"
        private const val MOBILE_NUMBER = "mobile_number"
        private const val POSITION = "position"
        private const val ORG_ID = "org_id"
        private const val OFFICE_ID = "office_id"
        private const val APPROVED = "approved"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
    }

    var userId: Int
        get() = sharedPreferences.getInt(USER_UID, -1)
        set(value) {
            sharedPreferences.edit().putInt(USER_UID, value).apply()
        }

    var name: String?
        get() = sharedPreferences.getString(USER_NAME, null)
        set(value) {
            sharedPreferences.edit().putString(USER_NAME, value).apply()
        }

    var email: String?
        get() = sharedPreferences.getString(USER_EMAIL, null)
        set(value) {
            sharedPreferences.edit().putString(USER_EMAIL, value).apply()
        }

    var employeeId: String?
        get() = sharedPreferences.getString(EMPLOYEE_ID, null)
        set(value) {
            sharedPreferences.edit().putString(EMPLOYEE_ID, value).apply()
        }

    var mobileNumber: String?
        get() = sharedPreferences.getString(MOBILE_NUMBER, null)
        set(value) {
            sharedPreferences.edit().putString(MOBILE_NUMBER, value).apply()
        }

    var position: String?
        get() = sharedPreferences.getString(POSITION, null)
        set(value) {
            sharedPreferences.edit().putString(POSITION, value).apply()
        }

    var orgId: Int
        get() = sharedPreferences.getInt(ORG_ID, -1)
        set(value) {
            sharedPreferences.edit().putInt(ORG_ID, value).apply()
        }

    var officeId: Int
        get() = sharedPreferences.getInt(OFFICE_ID, -1)
        set(value) {
            sharedPreferences.edit().putInt(OFFICE_ID, value).apply()
        }

    var approved: Int
        get() = sharedPreferences.getInt(APPROVED, 0)
        set(value) {
            sharedPreferences.edit().putInt(APPROVED, value).apply()
        }
    var latitude: String?
        get() = sharedPreferences.getString(LATITUDE, null)
        set(value) {
            sharedPreferences.edit().putString(LATITUDE, value).apply()
        }
    var longitude:String?
        get() = sharedPreferences.getString(LONGITUDE, null)
        set(value) {
            sharedPreferences.edit().putString(LONGITUDE, value).apply()
        }
}
