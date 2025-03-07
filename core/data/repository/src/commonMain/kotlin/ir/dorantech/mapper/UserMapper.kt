package ir.dorantech.mapper

import ir.dorantech.local.UserEntity
import ir.dorantech.domain.model.UserModel

internal fun UserEntity.toUserModel(): UserModel = UserModel(
    id = this.id.toInt(),
    username = this.username,
    email = this.email
)

