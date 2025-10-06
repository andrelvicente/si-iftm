import fs from 'fs';
import { pipeline } from '@xenova/transformers';
import { createRequire } from 'module';
const require = createRequire(import.meta.url);
const pdf = require('pdf-parse');

const TEXT_PATH = './data/regulamento.pdf';
const CHUNKS_PATH = './data/chunks.json';

function chunkText(text, maxLen = 1200) {
  const paras = text.replace(/\r/g, '').split(/\n\s*\n+/);
  const chunks = [];
  let buf = '';
  for (const p of paras) {
    const cand = buf ? `${buf}\n\n${p}` : p;
    if (cand.length > maxLen && buf) {
      chunks.push(buf.trim());
      buf = p;
    } else {
      buf = cand;
    }
  }
  if (buf.trim()) chunks.push(buf.trim());
  return chunks.map((t, i) => ({ id: i + 1, text: t }));
}

async function main() {
  // Read PDF file as binary and parse it
  const pdfBuffer = fs.readFileSync(TEXT_PATH);
  const pdfData = await pdf(pdfBuffer);
  const raw = pdfData.text.trim();
  
  console.log(`PDF extraído: ${raw.length} caracteres`);
  console.log(`Primeiros 200 caracteres: ${raw.substring(0, 200)}...`);
  
  const extractor = await pipeline('feature-extraction', 'Xenova/all-MiniLM-L6-v2');
  const chunks = chunkText(raw, 1200);
  const withEmb = [];
  for (const c of chunks) {
    const emb = await extractor(c.text, { pooling: 'mean', normalize: true });
    withEmb.push({ ...c, embedding: Array.from(emb.data) });
  }
  fs.writeFileSync(CHUNKS_PATH, JSON.stringify(withEmb, null, 2));
  console.log(`OK: ${withEmb.length} chunks salvos em ${CHUNKS_PATH}`);
}
main().catch(e => { console.error(e); process.exit(1); });