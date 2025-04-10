export function carregarComXHR(url) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
  
    xhr.onload = function () {
      if (xhr.status === 200) {
        const data = JSON.parse(xhr.responseText);
        console.log('XMLHttpRequest:', `Nome: ${data.nome}, Idade: ${data.idade}`);
      } else {
        console.error('Erro na requisição XHR:', xhr.statusText);
      }
    };
  
    xhr.onerror = function () {
      console.error('Erro de rede na requisição XHR');
    };
  
    xhr.send();
  }
  