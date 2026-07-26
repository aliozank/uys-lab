<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'

interface Airport {
  icaoCode: string
  airportName: string
  city: string
  country: string
}

interface FlightResponse {
  flightNumber: string
  originAirport: Airport
  destinationAirport: Airport
}

const airports = ref<Airport[]>([])
const flights = ref<FlightResponse[]>([])
const loadingAirports = ref(false)
const submitting = ref(false)

const form = reactive({
  flightNumber: '',
  originIcaoCode: '',
  destinationIcaoCode: '',
})

async function loadAirports() {
  loadingAirports.value = true

  try {
    const response = await fetch('/reference-api/airports')

    if (!response.ok) {
      throw new Error('Havalimanları alınamadı')
    }

    airports.value = await response.json()
  } catch {
    ElMessage.error('Reference Service çalışmıyor veya havalimanları alınamadı')
  } finally {
    loadingAirports.value = false
  }
}

async function createFlight() {
  const flightNumber = form.flightNumber.trim()

  if (!flightNumber || !form.originIcaoCode || !form.destinationIcaoCode) {
    ElMessage.warning('Bütün alanları doldurmalısın')
    return
  }

  if (form.originIcaoCode === form.destinationIcaoCode) {
    ElMessage.warning('Kalkış ve varış havalimanı aynı olamaz')
    return
  }

  submitting.value = true

  try {
    const response = await fetch('/flight-api/flights', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        flightNumber,
        originIcaoCode: form.originIcaoCode,
        destinationIcaoCode: form.destinationIcaoCode,
      }),
    })

    if (!response.ok) {
      throw new Error('Uçuş oluşturulamadı')
    }

    const createdFlight: FlightResponse = await response.json()
    flights.value.unshift(createdFlight)

    form.flightNumber = ''
    form.originIcaoCode = ''
    form.destinationIcaoCode = ''

    ElMessage.success('Uçuş başarıyla oluşturuldu')
  } catch {
    ElMessage.error('Flight Service çalışmıyor veya uçuş oluşturulamadı')
  } finally {
    submitting.value = false
  }
}

onMounted(loadAirports)
</script>

<template>
  <div class="app-shell">
    <header class="page-header">
      <div>
        <p class="eyebrow">UYS LAB • VUE 3 + ELEMENT PLUS</p>
        <h1>Uçuş Yönetim Paneli</h1>
        <p class="subtitle">
          Reference Service'ten havalimanı seç, Flight Service üzerinden uçuş oluştur.
        </p>
      </div>

      <el-tag type="success" effect="dark" round>Frontend Lab</el-tag>
    </header>

    <main class="content-grid">
      <el-card class="panel form-panel" shadow="never">
        <template #header>
          <div class="panel-heading">
            <div>
              <h2>Yeni uçuş</h2>
              <p>Uçuş numarası ve rota bilgilerini gir.</p>
            </div>
          </div>
        </template>

        <el-form label-position="top" @submit.prevent="createFlight">
          <el-form-item label="Uçuş numarası">
            <el-input
              v-model="form.flightNumber"
              placeholder="Örnek: TK101"
              maxlength="20"
              clearable
            />
          </el-form-item>

          <el-form-item label="Kalkış havalimanı">
            <el-select
              v-model="form.originIcaoCode"
              placeholder="Kalkış havalimanını seç"
              :loading="loadingAirports"
              filterable
              clearable
            >
              <el-option
                v-for="airport in airports"
                :key="`origin-${airport.icaoCode}`"
                :label="`${airport.icaoCode} — ${airport.airportName}`"
                :value="airport.icaoCode"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="Varış havalimanı">
            <el-select
              v-model="form.destinationIcaoCode"
              placeholder="Varış havalimanını seç"
              :loading="loadingAirports"
              filterable
              clearable
            >
              <el-option
                v-for="airport in airports"
                :key="`destination-${airport.icaoCode}`"
                :label="`${airport.icaoCode} — ${airport.airportName}`"
                :value="airport.icaoCode"
              />
            </el-select>
          </el-form-item>

          <el-button
            class="submit-button"
            type="primary"
            native-type="submit"
            :loading="submitting"
          >
            Uçuş oluştur
          </el-button>
        </el-form>
      </el-card>

      <el-card class="panel table-panel" shadow="never">
        <template #header>
          <div class="panel-heading">
            <div>
              <h2>Oluşturulan uçuşlar</h2>
              <p>Bu oturumda Flight Service'ten dönen sonuçlar.</p>
            </div>

            <el-tag type="info">{{ flights.length }} kayıt</el-tag>
          </div>
        </template>

        <el-empty
          v-if="flights.length === 0"
          description="Henüz uçuş oluşturulmadı"
        />

        <el-table v-else :data="flights" stripe>
          <el-table-column prop="flightNumber" label="Uçuş no" min-width="110" />

          <el-table-column label="Kalkış" min-width="220">
            <template #default="{ row }">
              <strong>{{ row.originAirport.icaoCode }}</strong>
              <span class="airport-name">{{ row.originAirport.airportName }}</span>
            </template>
          </el-table-column>

          <el-table-column label="Varış" min-width="220">
            <template #default="{ row }">
              <strong>{{ row.destinationAirport.icaoCode }}</strong>
              <span class="airport-name">{{ row.destinationAirport.airportName }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </main>
  </div>
</template>

<style scoped>
.app-shell {
  width: min(1180px, calc(100% - 32px));
  margin: 0 auto;
  padding: 48px 0 64px;
}

.page-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 24px;
  margin-bottom: 28px;
}

.eyebrow {
  margin: 0 0 8px;
  color: #409eff;
  font-size: 12px;
  font-weight: 800;
  letter-spacing: 0.12em;
}

h1,
h2,
p {
  margin-top: 0;
}

h1 {
  margin-bottom: 10px;
  color: #172033;
  font-size: clamp(30px, 4vw, 46px);
  line-height: 1.1;
}

.subtitle {
  max-width: 680px;
  margin-bottom: 0;
  color: #697386;
  font-size: 16px;
}

.content-grid {
  display: grid;
  grid-template-columns: minmax(280px, 380px) minmax(0, 1fr);
  gap: 22px;
  align-items: start;
}

.panel {
  border: 1px solid #e3e9f2;
  border-radius: 16px;
}

.panel-heading {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.panel-heading h2 {
  margin-bottom: 4px;
  font-size: 18px;
}

.panel-heading p {
  margin-bottom: 0;
  color: #8a94a6;
  font-size: 13px;
}

.form-panel :deep(.el-select) {
  width: 100%;
}

.submit-button {
  width: 100%;
  margin-top: 4px;
}

.airport-name {
  display: block;
  margin-top: 3px;
  color: #8a94a6;
  font-size: 12px;
}

@media (max-width: 820px) {
  .app-shell {
    width: min(100% - 24px, 680px);
    padding-top: 28px;
  }

  .page-header {
    flex-direction: column;
  }

  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
