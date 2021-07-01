package com.diksha.employeedata.ModelClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomModel {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var uid = 0

    @ColumnInfo(name = "first_name")
    var firstname: String

    @ColumnInfo(name = "last_name")
    var lastname: String
    var isExpanded = false

    constructor(firstname: String, lastname: String, age: String, gender: String, picture: String?,
        exp: String?, org: String?, role: String?, degree: String?, institute: String?
    ) {
        this.firstname = firstname
        this.lastname = lastname
        this.age = age
        this.gender = gender
        this.picture = picture
        this.exp = exp
        organization = org
        this.role = role
        this.degree = degree
        institution = institute
    }

    constructor(firstname: String, lastname: String, age: String, gender: String) {
        this.firstname = firstname
        this.lastname = lastname
        this.age = age
        this.gender = gender
    }



    @ColumnInfo(name = "age")
    var age: String

    @ColumnInfo(name = "gender")
    var gender: String

    @ColumnInfo(name = "picture")
    var picture: String? = null

    @ColumnInfo(name = "exp")
    var exp: String? = null

    @ColumnInfo(name = "org")
    var organization: String? = null
        get() = field
        set(organization) {
            field = organization
        }

    @ColumnInfo(name = "role")
    var role: String? = null

    @ColumnInfo(name = "degree")
    var degree: String? = null

    @ColumnInfo(name = "institute")
    var institution: String? = null
        get() = field
        set(institution) {
            field = institution
        }
}