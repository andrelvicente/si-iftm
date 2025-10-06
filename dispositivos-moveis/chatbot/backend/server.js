import express from 'express';
import cors from 'cors';
import fs from 'fs';
import cosine from 'cosine-similarity';
import { pipeline } from '@xenova/transformers';

const app = express();
app.use(cors());
app.use(express.json({ limit: '1mb' }));

const CHUNKS = JSON.parse(fs.readFileSync('./data/chunks.json', 'utf-8'));
const PORT = 3000;

const load = async () => ({
  embedder: await pipeline('feature-extraction', 'Xenova/all-MiniLM-L6-v2'),
});
const { embedder } = await load();

const topK = (qEmb, k = 3) =>
  CHUNKS.map(c => ({ ...c, score: cosine(qEmb, c.embedding) }))
        .sort((a,b)=>b.score-a.score)
        .slice(0,k);

// Health check endpoint
app.get('/health', (req, res) => {
  res.json({ status: 'ok', message: 'Server is running' });
});

app.post('/ask', async (req, res) => {
  try {
    const question = (req.body?.question || '').trim();
    if (question.length < 3) return res.status(400).json({ error: 'Pergunta muito curta.' });

    const q = await embedder(question, { pooling: 'mean', normalize: true });
    const qEmb = Array.from(q.data);
    const cands = topK(qEmb, 3);

    // Simple answer extraction - return the most relevant chunk
    const bestChunk = cands[0]; // The most similar chunk
    const answer = bestChunk ? bestChunk.text.substring(0, 500) + '...' : 'Nenhuma informação relevante encontrada.';

    res.json({
      answer: answer,
      sources: cands.map(c => ({ id: c.id, sim: c.score })),
      score: bestChunk ? bestChunk.score : 0,
    });
  } catch (e) {
    console.error(e);
    res.status(500).json({ error: 'Falha ao processar a pergunta.' });
  }
});

app.listen(PORT, '0.0.0.0', () => console.log(`API em http://0.0.0.0:${PORT} (acessível em http://192.168.94.80:${PORT})`));