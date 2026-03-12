<template>
  <div>
    <div class="page-header">
      <h3>房间管理</h3>
      <el-button type="primary" @click="openDialog()">
        <el-icon><Plus /></el-icon> 新增房间
      </el-button>
    </div>

    <div class="filter-bar">
      <el-select v-model="filterHotelId" clearable placeholder="按酒店筛选" style="width:200px" @change="fetchData">
        <el-option v-for="h in hotelOptions" :key="h.id" :label="h.name" :value="h.id" />
      </el-select>
    </div>

    <el-table v-loading="loading" :data="tableData" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="房间名称" />
      <el-table-column prop="hotelId" label="所属酒店" width="100" />
      <el-table-column prop="price" label="价格" width="120">
        <template #default="{ row }">¥{{ row.price }}</template>
      </el-table-column>
      <el-table-column prop="area" label="面积(㎡)" width="100" />
      <el-table-column prop="floor" label="楼层" width="80" />
      <el-table-column prop="stock" label="库存" width="80" />
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
    <el-dialog v-model="dialogVisible" :title="editId ? '编辑房间' : '新增房间'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="所属酒店" prop="hotelId">
          <el-select v-model="form.hotelId" placeholder="请选择酒店" style="width:100%">
            <el-option v-for="h in hotelOptions" :key="h.id" :label="h.name" :value="h.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="房间名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :precision="2" :min="0" />
        </el-form-item>
        <el-form-item label="面积(㎡)">
          <el-input-number v-model="form.area" :min="0" />
        </el-form-item>
        <el-form-item label="楼层">
          <el-input-number v-model="form.floor" :min="1" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="form.stock" :min="0" />
        </el-form-item>
        <el-form-item label="朝向">
          <el-input v-model="form.orientation" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
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
import { getRooms, createRoom, updateRoom, deleteRoom } from '@/api/room'
import { getHotels } from '@/api/hotel'

const loading = ref(false)
const saving = ref(false)
const tableData = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const filterHotelId = ref(null)
const dialogVisible = ref(false)
const editId = ref(null)
const formRef = ref()
const hotelOptions = ref([])

const form = reactive({
  hotelId: null, name: '', price: 0, area: null,
  floor: null, orientation: '', stock: 0, description: '', status: 1,
})

const rules = {
  hotelId: [{ required: true, message: '请选择酒店', trigger: 'change' }],
  name: [{ required: true, message: '请输入房间名称', trigger: 'blur' }],
  price: [{ required: true, message: '请输入价格', trigger: 'blur' }],
}

const fetchHotels = async () => {
  const res = await getHotels({ page: 1, size: 100 })
  hotelOptions.value = res.data.records || res.data || []
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getRooms({
      page: page.value,
      size: size.value,
      hotelId: filterHotelId.value || undefined,
    })
    tableData.value = res.data.records || res.data
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

const openDialog = (row = null) => {
  editId.value = row?.id || null
  Object.assign(form, {
    hotelId: row?.hotelId || null,
    name: row?.name || '',
    price: row?.price || 0,
    area: row?.area || null,
    floor: row?.floor || null,
    orientation: row?.orientation || '',
    stock: row?.stock ?? 0,
    description: row?.description || '',
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
      await updateRoom(editId.value, form)
    } else {
      await createRoom(form)
    }
    ElMessage.success('保存成功')
    dialogVisible.value = false
    fetchData()
  } finally {
    saving.value = false
  }
}

const handleDelete = async (id) => {
  await deleteRoom(id)
  ElMessage.success('删除成功')
  fetchData()
}

onMounted(() => {
  fetchHotels()
  fetchData()
})
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
.page-header h3 { margin: 0; font-size: 18px; }
.filter-bar { margin-bottom: 16px; }
.pagination { margin-top: 16px; justify-content: flex-end; }
</style>
