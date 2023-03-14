<template>
  <div style="width: 400px; margin: 150px auto">
    <h1 style="text-align: center; margin-bottom: 30px">登 陆</h1>
    <el-form :model="user" size="large" :rules="rules" ref="ruleFormRef">
      <el-form-item prop="username">
        <el-input
          v-model="user.username"
          placeholder="请输入姓名"
          :prefix-icon="User"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="user.password"
          placeholder=" 请输入密码"
          :prefix-icon="Lock"
          show-password
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          size="default"
          style="width: 100%"
          @click="login"
          >登陆</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive, getCurrentInstance } from "vue";
import { User, Lock } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import request from "../request";
import router from "../router";

const { proxy } = getCurrentInstance();

const user = reactive({
  // username: "张三",
  // password: "",
});

const rules = reactive({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
});

const login = () => {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      request.post("/user/login", user).then((res) => {
        if (res.code === "200") {
          ElMessage({
            type: "success",
            message: "成功登陆！",
          });
          router.push("/home");
        } else {
          ElMessage({
            type: "error",
            message: res.msg,
          });
        }
      });
    } else {
      ElMessage({
        type: "error",
        message: "错误",
      });
    }
  });
};
</script>
