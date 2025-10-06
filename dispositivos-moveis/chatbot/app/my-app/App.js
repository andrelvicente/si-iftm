import { useState, useRef } from 'react';
import { View, Text, TextInput, Pressable, FlatList, SafeAreaView, KeyboardAvoidingView, Platform, ActivityIndicator } from 'react-native';

// Para desenvolvimento mobile, use o IP da sua máquina ao invés de localhost
const BASE_URL = 'http://192.168.94.80:3000';

export default function App() {
  const [history, setHistory] = useState([]);
  const [q, setQ] = useState('');
  const [loading, setLoading] = useState(false);
  const listRef = useRef(null);

  console.log('🚀 [DEBUG] App inicializado - BASE_URL:', BASE_URL);
  console.log('📊 [DEBUG] Estado atual:', { historyLength: history.length, loading, qLength: q.length });

  async function ask() {
    const question = q.trim();
    console.log('🔍 [DEBUG] Iniciando pergunta:', question);
    
    if (!question) {
      console.log('⚠️ [DEBUG] Pergunta vazia, cancelando envio');
      return;
    }
    
    setQ('');
    setHistory(h => [...h, { role: 'user', text: question }]);
    setLoading(true);
    console.log('📤 [DEBUG] Enviando pergunta para API:', BASE_URL + '/ask');
    
    try {
      const r = await fetch(`${BASE_URL}/ask`, { method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({ question }) });
      console.log('📡 [DEBUG] Resposta recebida - Status:', r.status, 'OK:', r.ok);
      
      const data = await r.json();
      console.log('📋 [DEBUG] Dados da resposta:', JSON.stringify(data, null, 2));
      
      const meta = data.sources?.map(s => `#${s.id} (sim=${s.sim.toFixed(2)})`).join('  ');
      console.log('🏷️ [DEBUG] Metadados das fontes:', meta);
      
      setHistory(h => [...h, { role: 'bot', text: data.answer, meta }]);
      console.log('✅ [DEBUG] Resposta adicionada ao histórico');
    } catch (error) {
      console.error('❌ [DEBUG] Erro na requisição:', error);
      setHistory(h => [...h, { role: 'bot', text: 'Erro ao consultar o regulamento.' }]);
    } finally {
      setLoading(false);
      console.log('🔄 [DEBUG] Loading finalizado, rolando para o final');
      setTimeout(()=>listRef.current?.scrollToEnd({animated:true}), 100);
    }
  }

  const Bubble = ({ item }) => {
    console.log('💬 [DEBUG] Renderizando bubble:', { role: item.role, textLength: item.text?.length, hasMeta: !!item.meta });
    return (
      <View style={{ alignSelf: item.role==='user'?'flex-end':'flex-start', maxWidth:'90%', marginVertical:6 }}>
        <View style={{ backgroundColor:item.role==='user'?'#2563eb':'#111827', padding:12, borderRadius:12 }}>
          <Text style={{ color:'white', fontSize:16 }}>{item.text}</Text>
          {!!item.meta && <Text style={{ color:'#9ca3af', marginTop:6, fontSize:12 }}>{item.meta}</Text>}
        </View>
      </View>
    );
  };

  return (
    <SafeAreaView style={{ flex:1, backgroundColor:'#0b1220' }}>
      <KeyboardAvoidingView style={{ flex:1 }} behavior={Platform.OS==='ios'?'padding':undefined}>
        <FlatList ref={listRef} style={{ flex:1, padding:12 }} data={history} keyExtractor={(_,i)=>String(i)} renderItem={Bubble} />
        <View style={{ flexDirection:'row', padding:10, gap:8, borderTopWidth:1, borderTopColor:'#1f2937' }}>
          <TextInput
            style={{ flex:1, backgroundColor:'#111827', color:'white', padding:12, borderRadius:10 }}
            placeholder="Pergunte algo do regulamento..."
            placeholderTextColor="#6b7280"
            value={q}
            onChangeText={(text) => {
              console.log('⌨️ [DEBUG] Texto alterado:', text.length, 'caracteres');
              setQ(text);
            }}
            onSubmitEditing={() => {
              console.log('📤 [DEBUG] Enter pressionado no input');
              ask();
            }}
            returnKeyType="send"
          />
          <Pressable 
            onPress={() => {
              console.log('🔘 [DEBUG] Botão enviar pressionado, loading:', loading);
              ask();
            }} 
            disabled={loading} 
            style={{ backgroundColor:'#22c55e', paddingHorizontal:16, borderRadius:10, justifyContent:'center' }}
          >
            {loading ? <ActivityIndicator color="black" /> : <Text style={{ color:'black', fontWeight:'700' }}>Enviar</Text>}
          </Pressable>
        </View>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
}