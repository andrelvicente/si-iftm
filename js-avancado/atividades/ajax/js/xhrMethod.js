export function carregarComXHR(url) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
  
    xhr.onload = function () {
      if (xhr.status != 200) {
        throw new Error('Erro na requisição XHR');
      }

      const data = JSON.parse(xhr.responseText);
      console.log('XMLHttpRequest:', `Nome: ${data.nome}, Idade: ${data.idade}`);

    };
  
    xhr.onerror = function () {
      console.error('Erro na requisição XHR');
    };
  
    xhr.send();
  }
  