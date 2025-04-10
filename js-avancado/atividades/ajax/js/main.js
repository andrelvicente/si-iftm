import { carregarComFetch } from './fetchMethod.js';
import { carregarComXHR } from './xhrMethod.js';

const url = 'https://raw.githsercontent.com/andrelvicente/si-iftm/main/js-avancado/atividades/ajax/dados.json';

carregarComFetch(url);
carregarComXHR(url);
