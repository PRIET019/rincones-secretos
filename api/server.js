const express = require("express");
const cors = require("cors");
const fs = require("fs");
const path = require("path");

const app = express();
app.use(cors());
app.use(express.json({ limit: "1mb" }));

// ===== Persistencia en fichero =====
const DATA_DIR = path.join(__dirname, "data");
const DATA_FILE = path.join(DATA_DIR, "rincones.json");

// El fichero guardará: { nextId: number, rincones: [...] }
function ensureDataFile() {
  if (!fs.existsSync(DATA_DIR)) fs.mkdirSync(DATA_DIR, { recursive: true });

  if (!fs.existsSync(DATA_FILE)) {
    const seeded = seedInitialData();
    fs.writeFileSync(DATA_FILE, JSON.stringify(seeded, null, 2), "utf-8");
  }
}

function readData() {
  const raw = fs.readFileSync(DATA_FILE, "utf-8");
  return JSON.parse(raw);
}

function writeData(data) {
  // escritura "atómica" simple: escribimos a temp y renombramos
  const tmp = DATA_FILE + ".tmp";
  fs.writeFileSync(tmp, JSON.stringify(data, null, 2), "utf-8");
  fs.renameSync(tmp, DATA_FILE);
}

function seedInitialData() {
  const now = Date.now();
  const base = [
    { name: "Mirador del atardecer", category: "Mirador", description: "Vistas top para el final del día", phone: "+34 600 111 222", latitude: 40.4168, longitude: -3.7038 },
    { name: "Mural de colores", category: "Arte", description: "Un mural enorme en una calle tranquila", phone: "+34 600 222 333", latitude: 40.4152, longitude: -3.7074 },
    { name: "Parque silencioso", category: "Naturaleza", description: "Perfecto para leer 20 minutos", phone: "+34 600 333 444", latitude: 40.4201, longitude: -3.7052 },
    { name: "Café con vistas", category: "Cafetería", description: "Terraza con buena luz", phone: "+34 600 444 555", latitude: 40.4189, longitude: -3.7101 },
    { name: "Plaza escondida", category: "Ciudad", description: "Pequeña plaza poco transitada", phone: "+34 600 555 666", latitude: 40.4134, longitude: -3.7019 },
    { name: "Librería curiosa", category: "Cultura", description: "Pasillo de libros raros y cómics", phone: "+34 600 666 777", latitude: 40.4174, longitude: -3.6996 },
    { name: "Puente fotogénico", category: "Foto", description: "Buen sitio para fotos nocturnas", phone: "+34 600 777 888", latitude: 40.4196, longitude: -3.7122 },
    { name: "Mercadillo del finde", category: "Ocio", description: "Puestos y ambiente los domingos", phone: "+34 600 888 999", latitude: 40.4109, longitude: -3.7080 },
    { name: "Rincón para picnic", category: "Naturaleza", description: "Sombra y bancos", phone: "+34 600 999 000", latitude: 40.4217, longitude: -3.7007 },
    { name: "Mirador urbano", category: "Mirador", description: "Ves media ciudad desde aquí", phone: "+34 611 000 111", latitude: 40.4141, longitude: -3.7135 }
  ];

  const rincones = base.map((r, i) => ({
    id: i + 1,
    name: r.name,
    description: r.description ?? "",
    category: r.category,
    phone: r.phone,
    latitude: r.latitude,
    longitude: r.longitude,
    createdAt: now - (base.length - i) * 60000 // escalonado por minutos
  }));

  return { nextId: rincones.length + 1, rincones };
}

// ===== Validación =====
function isNonEmptyString(v) {
  return typeof v === "string" && v.trim().length > 0;
}
function isFiniteNumber(v) {
  return typeof v === "number" && Number.isFinite(v);
}

function validateRincon(body) {
  const errors = [];

  if (!isNonEmptyString(body.name)) errors.push("name es obligatorio (string no vacío).");
  if (!isNonEmptyString(body.category)) errors.push("category es obligatorio (string no vacío).");
  if (!isNonEmptyString(body.phone)) errors.push("phone es obligatorio (string no vacío).");

  if (!isFiniteNumber(body.latitude)) errors.push("latitude es obligatoria (number).");
  if (!isFiniteNumber(body.longitude)) errors.push("longitude es obligatoria (number).");

  if (body.description != null && typeof body.description !== "string") {
    errors.push("description debe ser string si se envía.");
  }

  if (isFiniteNumber(body.latitude) && (body.latitude < -90 || body.latitude > 90)) {
    errors.push("latitude fuera de rango (-90..90).");
  }
  if (isFiniteNumber(body.longitude) && (body.longitude < -180 || body.longitude > 180)) {
    errors.push("longitude fuera de rango (-180..180).");
  }

  // Reforzamos el requisito: no aceptamos foto en remoto
  if (body.photoUri != null) {
    errors.push("photoUri no se acepta (la foto es solo local).");
  }

  return errors;
}

// ===== Init =====
ensureDataFile();

// ===== Endpoints =====
app.get("/health", (req, res) => {
  const data = readData();
  res.status(200).json({ ok: true, count: data.rincones.length });
});

app.get("/rincones", (req, res) => {
  const data = readData();
  // más recientes primero
  const sorted = [...data.rincones].sort((a, b) => b.createdAt - a.createdAt);
  res.status(200).json(sorted);
});

app.post("/rincones", (req, res) => {
  const errors = validateRincon(req.body);
  if (errors.length > 0) {
    return res.status(400).json({ error: "VALIDATION_ERROR", details: errors });
  }

  const data = readData();

  const rincon = {
    id: data.nextId++,
    name: req.body.name.trim(),
    description: (req.body.description ?? "").toString(),
    category: req.body.category.trim(),
    phone: req.body.phone.trim(),
    latitude: req.body.latitude,
    longitude: req.body.longitude,
    createdAt: Date.now()
  };

  data.rincones.push(rincon);
  writeData(data);

  res.status(201).json(rincon);
});

const PORT = process.env.PORT || 3001;
app.listen(PORT, "0.0.0.0", () => {
  console.log(`Rincones API escuchando en http://localhost:${PORT}`);
  console.log(`- GET  /rincones`);
  console.log(`- POST /rincones`);
  console.log(`Datos en: ${DATA_FILE}`);
});