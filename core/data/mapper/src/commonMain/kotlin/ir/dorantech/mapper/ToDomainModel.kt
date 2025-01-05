package ir.dorantech.mapper


import ir.dorantech.domain.model.UserModel
import ir.dorantech.remote.dto.UserDto

fun UserDto.toDomain(): UserModel {
    return UserModel(id = this.id, name = this.name, email = this.email)
}