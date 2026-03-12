<template>
  <div>
    <div class="page-header">
      <h3>酒店管理</h3>
      <el-button type="primary" @click="openDialog()">
        <el-icon><Plus /></el-icon> 新增酒店
      </el-button>
    </div>

    <el-table v-loading="loading" :data="tableData" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="酒店名称" />
      <el-table-column prop="address" label="地址" />
      <el-table-column prop="phone" label="联系电话" />
      <el-table-column prop="minPrice" label="最低价格" width="120">
        <template #default="{ row }">¥{{ row.minPrice }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="90">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '上架' : '下架' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="openDialog(row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(row.id)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      class="pagination"
      v-model:current-page="page"
      v-model:page-size="size"
      :total="total"
      :page-sizes="[10, 20, 50]"
      layout="total, sizes, prev, pager, next"
      @change="fetchData"
    />

    <!-- Dialog -->
    <el-dialog v-model="dialogVisible" :title="editId ? '编辑酒店' : '新增酒店'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="酒店名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.intro" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="最低价格">
          <el-input-number v-model="form.minPrice" :precision="2" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="上架" :value="1" />
            <el-option label="下架" :value="0" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { getHotels, createHotel, updateHotel, deleteHotel } from '@/api/hotel'

const loading = ref(false)
const saving = ref(false)
const tableData = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const dialogVisible = ref(false)
const editId = ref(null)
const formRef = ref()

const form = reactive({
  name: '', address: '', phone: '', intro: '',
  minPrice: 0, status: 1,
})

const rules = {
  name: [{ required: true, message: '请输入酒店名称', trigger: 'blur' }],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getHotels({ page: page.value, size: size.value })
    tableData.value = res.data.records || res.data
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openDialog = (row = null) => {
  editId.value = row?.id || null
  Object.assign(form, {
    name: row?.name || '',
    address: row?.address || '',
    phone: row?.phone || '',
    intro: row?.intro || '',
    minPrice: row?.minPrice || 0,
    status: row?.status ?? 1,
  })
  dialogVisible.value = true
}

const handleSave = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return
  saving.value = true
  try {
    if (editId.value) {
      await updateHotel(editId.value, form)
    } else {
      await createHotel(form)
    }
    ElMessage.success('保存成功')
    dialogVisible.value = false
    fetchData()
  } finally {
    saving.value = false
  }
}

const handleDelete = async (id) => {
  await deleteHotel(id)
  ElMessage.success('删除成功')
  fetchData()
}

onMounted(fetchData)
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
.page-header h3 {
  margin: 0;
  font-size: 18px;
}
.pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>
