<template>
    <div>
        <el-input
            v-model="username"
            style="width: 260px"
            placeholder="请输入用户名"
            size="normal"
            clearable
            @change=""
        ></el-input>
        <el-input
            v-model="phone"
            style="width: 260px"
            placeholder="请输入手机号码"
            size="normal"
            clearable
            @change=""
        ></el-input>
        <el-input
            v-model="email"
            style="width: 260px"
            placeholder="请输入邮箱"
            size="normal"
            clearable
            @change=""
        ></el-input>
        <el-button
            type="success"
            size="default"
            @click="load"
            style="margin-left: 10px"
            ><el-icon style="margin-right: 3px"><Search /></el-icon
            >搜索</el-button
        >

        <el-button type="primary" @click="handleAdd"
            ><el-icon style="margin-right: 3px"><Plus /></el-icon
            >新增</el-button
        >
    </div>
    <el-table :data="state.tableData" stripe style="width: 100%">
        <el-table-column prop="id" label="ID主键" width="180" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="sex" label="性别" />
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="phone" label="电话" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="avatar" label="头像" />
        <el-table-column label="操作">
            <template #default="scope">
                <el-button
                    type="primary"
                    @click="handleEdit(scope.row)"
                    size="small"
                    ><el-icon style="margin-right: 3px"><Edit /></el-icon
                    >编辑</el-button
                >
                <el-popconfirm
                    title="确定删除吗"
                    @confirm="deleteRow(scope.row.id)"
                >
                    <template #reference>
                        <el-button type="danger" size="small"
                            ><el-icon style="margin-right: 3px">
                                <Delete /></el-icon
                            >删除</el-button
                        ></template
                    >
                </el-popconfirm>
            </template>
        </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
        <el-pagination
            v-model:page-size="pageSize"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            v-model:currentPage="currentPage"
            :page-sizes="[1, 2, 4, 8]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            background
        >
            :pager-count="7">
        </el-pagination>
    </div>

    <el-dialog title="用户信息" v-model="dialogFormVisible" width="40%">
        <el-form
            :model="state.form"
            label-width="120px"
            style="width: 85%"
            :rules="state.rules"
            ref="ruleFormRef"
        >
            <el-form-item label="用户名" prop="username">
                <el-input
                    v-model="state.form.username"
                    placeholder="请输入用户名"
                />
            </el-form-item>
            <el-form-item label="年龄" prop="sex">
                <el-input v-model="state.form.sex" placeholder="请输入年龄" />
            </el-form-item>
            <el-form-item label="性别" prop="age">
                <el-radio-group v-model="state.form.age">
                    <el-radio label="男"></el-radio>
                    <el-radio label="女"></el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
                <el-input
                    v-model="state.form.phone"
                    placeholder="请输入手机号"
                />
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="state.form.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="地址" prop="address">
                <el-input
                    type="textarea"
                    v-model="state.form.address"
                    placeholder="请输入地址"
                />
            </el-form-item>
            <el-form-item label="头像" prop="avatar">
                <el-input
                    v-model="state.form.avatar"
                    placeholder="请输入头像"
                />
            </el-form-item>
        </el-form>

        <template #footer>
            <span>
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="save">确认</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { Plus, Edit, Delete, Search } from "@element-plus/icons-vue";
import { getCurrentInstance, reactive, ref } from "vue";
import request from "../request";
import { ElMessage } from "element-plus";

const username = ref("");
const phone = ref("");
const email = ref("");
const { proxy } = getCurrentInstance();
const checkEmail = (rule, value, callback) => {
    if (
        !/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$/.test(
            value
        )
    ) {
        return callback(new Error("邮箱格式错误!"));
    }
    callback();
};
let dialogFormVisible = ref(false);
let state = reactive({
    tableData: [],
    // { username: "123", password: 321 }
    form: {},
    rules: {
        username: [
            { required: true, message: "请输入用户名", trigger: "blur" },
            { min: 2, message: "最少两位" },
        ],
        // email: [{ validator: checkEmail, trigger: "blur" }],
    },
});
const currentPage = ref(1);
const pageSize = ref(1);
const total = ref(0);
const handleSizeChange = (val) => {
    pageSize.value = val;
    load();
};
const handleCurrentChange = (val) => {
    currentPage.value = val;
    load();
};
// request.get("/user").then((res) => {
//   state.tableData = res.data;
// });

const handleAdd = () => {
    dialogFormVisible.value = true;
    state.form = {};
};

const handleEdit = (row) => {
    dialogFormVisible.value = true;
    state.form = JSON.parse(JSON.stringify(row));
};
const load = () => {
    request
        .get("/user/page", {
            params: {
                currentPage: currentPage.value,
                pageSize: pageSize.value,
                username: username.value,
                phone: phone.value,
                email: email.value,
            },
        })
        .then((res) => {
            if (res.code === "200") {
                total.value = res.data.total;
                state.tableData = res.data.data;
            }
        });
    console.log("no problem");
};
load();
const save = () => {
    proxy.$refs.ruleFormRef.validate((valid) => {
        if (valid) {
            state.form.password = "123";
            console.log(state.form);
            if (state.form.id) {
                request.put("/user", state.form).then((res) => {
                    if (res.code === "200") {
                        ElMessage.success("保存成功！");
                        dialogFormVisible.value = false;
                        load();
                    } else {
                        ElMessage.error(res.msg);
                    }
                });
            } else {
                console.log("asdasdasdad");
                console.log(state.form);
                request.post("/user", state.form).then((res) => {
                    if (res.code === "200") {
                        ElMessage.success("新增成功！");
                        dialogFormVisible.value = false;
                        load();
                    } else {
                        ElMessage.error(res.msg);
                    }
                });
            }
        }
    });
};
const deleteRow = (id) => {
    console.log("----------------");
    request.delete("/user/" + id).then((res) => {
        if (res.code === "200" && res.data) {
            ElMessage.success("删除成功");
        } else {
            ElMessage.error("删除失败");
        }
        load();
    });
};
</script>
