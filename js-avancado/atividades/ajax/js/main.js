import { carregarComFetch } from './fetchMethod.js';
import { carregarComXHR } from './xhrMethod.js';

const url = 'https://github.com/andrelvicente/si-iftm/blob/main/js-avancado/atividades/ajax/dados.json';

carregarComFetch(url);
carregarComXHR(url);
