package com.example.a24012011122_mad_pr7

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val createTableQuery = """
            CREATE TABLE $TABLE_CONTACT (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $KEY_NAME TEXT,
                $KEY_PHONE_NO TEXT,
                $KEY_EMAIL TEXT,
                $KEY_ADDRESS TEXT,
                $KEY_LATITUDE REAL,
                $KEY_LONGITUDE REAL
            )
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACT")
        onCreate(db)
    }

    fun addContact(contact: Contact): Long {

        val db = writableDatabase

        val values = ContentValues().apply {
            put(KEY_NAME, contact.name)
            put(KEY_PHONE_NO, contact.phoneNo)
            put(KEY_EMAIL, contact.emailId)
            put(KEY_ADDRESS, contact.address)
            put(KEY_LATITUDE, contact.latitude)
            put(KEY_LONGITUDE, contact.longitude)
        }

        val result = db.insert(TABLE_CONTACT, null, values)

        db.close()

        return result
    }

    fun getContactByName(name: String): Contact? {

        val db = readableDatabase

        val cursor = db.query(
            TABLE_CONTACT,
            arrayOf(
                KEY_ID,
                KEY_NAME,
                KEY_PHONE_NO,
                KEY_EMAIL,
                KEY_ADDRESS,
                KEY_LATITUDE,
                KEY_LONGITUDE
            ),
            "$KEY_NAME = ?",
            arrayOf(name),
            null,
            null,
            null
        )

        var contact: Contact? = null

        if (cursor.moveToFirst()) {

            contact = Contact(
                id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(KEY_ID)
                ),

                name = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_NAME)
                ),

                phoneNo = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_PHONE_NO)
                ),

                emailId = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_EMAIL)
                ),

                address = cursor.getString(
                    cursor.getColumnIndexOrThrow(KEY_ADDRESS)
                ),

                latitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(KEY_LATITUDE)
                ),

                longitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(KEY_LONGITUDE)
                )
            )
        }

        cursor.close()
        db.close()

        return contact
    }

    fun getAllContacts(): ArrayList<Contact> {

        val contactList = ArrayList<Contact>()

        val db = readableDatabase

        val cursor = db.query(
            TABLE_CONTACT,
            null,
            null,
            null,
            null,
            null,
            "$KEY_ID DESC"
        )

        if (cursor.moveToFirst()) {

            do {

                val contact = Contact(

                    id = cursor.getLong(
                        cursor.getColumnIndexOrThrow(KEY_ID)
                    ),

                    name = cursor.getString(
                        cursor.getColumnIndexOrThrow(KEY_NAME)
                    ),

                    phoneNo = cursor.getString(
                        cursor.getColumnIndexOrThrow(KEY_PHONE_NO)
                    ),

                    emailId = cursor.getString(
                        cursor.getColumnIndexOrThrow(KEY_EMAIL)
                    ),

                    address = cursor.getString(
                        cursor.getColumnIndexOrThrow(KEY_ADDRESS)
                    ),

                    latitude = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(KEY_LATITUDE)
                    ),

                    longitude = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(KEY_LONGITUDE)
                    )
                )

                contactList.add(contact)

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return contactList
    }

    fun deleteContact(id: Long): Int {

        val db = writableDatabase

        val result = db.delete(
            TABLE_CONTACT,
            "$KEY_ID = ?",
            arrayOf(id.toString())
        )

        db.close()

        return result
    }

    companion object {

        private const val DB_NAME = "ContactDatabase.db"

        /*
         * Increase this whenever you change the database structure.
         */
        private const val DB_VERSION = 2

        private const val TABLE_CONTACT = "contacts"

        private const val KEY_ID = "id"

        private const val KEY_NAME = "name"

        private const val KEY_PHONE_NO = "phone_no"

        private const val KEY_EMAIL = "email"

        private const val KEY_ADDRESS = "address"

        private const val KEY_LATITUDE = "latitude"

        private const val KEY_LONGITUDE = "longitude"
    }
}