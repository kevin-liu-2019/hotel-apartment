<template>
  <div>
    <div class="page-header">
      <h3>订单管理</h3>
    </div>

    <div class="filter-bar">
      <el-select v-model="filterHotelId" clearable placeholder="按酒店筛选" style="width:200px;margin-right:12px" @change="fetchData">
        <el-option v-for="h in hotelOptions" :key="h.id" :label="h.name" :value="h.id" />
      </el-select>
      <el-select v-model="filterStatus" clearable placeholder="按状态筛选" style="width:160px" @change="fetchData">
        <el-option label="待支付" :value="1" />
        <el-option label="已支付" :value="2" />
        <el-option label="入住中" :value="3" />
        <el-option label="已完成" :value="4" />
        <el-option label="已取消" :value="5" />
      </el-select>
    </div>

    <el-table v-loading="loading" :data="tableData" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="orderNo" label="订单号" width="180" />
      <el-table-column prop="userId" label="用户ID" width="90" />
      <el-table-column prop="hotelId" label="酒店ID" width="90" />
      <el-table-column prop="roomId" label="房间ID" width="90" />
      <el-table-column prop="startDate" label="开始日期" width="120" />
      <el-table-column prop="endDate" label="结束日期" width="120" />
      <el-table-column prop="totalPrice" label="总价" width="120">
        <template #default="{ row }">¥{{ row.totalPrice }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="statusType(row.status)">{{ statusText(row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="170" />
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getOrders } from '@/api/order'
import { getHotels } from '@/api/hotel'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const page = ref(1)
const size = ref(10)
const filterHotelId = ref(null)
const filterStatus = ref(null)
const hotelOptions = ref([])

const STATUS_MAP = {
  1: { text: '待支付', type: 'warning' },
  2: { text: '已支付', type: 'primary' },
  3: { text: '入住中', type: 'success' },
  4: { text: '已完成', type: 'info' },
  5: { text: '已取消', type: 'danger' },
}
const statusText = (s) => STATUS_MAP[s]?.text || `状态${s}`
const statusType = (s) => STATUS_MAP[s]?.type || 'info'

const fetchHotels = async () => {
  const res = await getHotels({ page: 1, size: 100 })
  hotelOptions.value = res.data.records || res.data || []
}

const fetchData = async () => {
  loading.value = true
  try {
    const res = await getOrders({
      page: page.value,
      size: size.value,
      hotelId: filterHotelId.value || undefined,
      status: filterStatus.value ?? undefined,
    })
    tableData.value = res.data.records || res.data
    total.value = res.data.total || 0
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchHotels()
  fetchData()
})
</script>

<style scoped>
.page-header {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}
.page-header h3 { margin: 0; font-size: 18px; }
.filter-bar { margin-bottom: 16px; }
.pagination { margin-top: 16px; justify-content: flex-end; }
</style>
