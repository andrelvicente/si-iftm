import React from "react";
import { FloatingWhatsApp } from "react-floating-whatsapp";

export default function Whatsapp() {
  return (
    <FloatingWhatsApp
      phoneNumber="+5534984078584"
      accountName="<NOME DO CLIENTE>: como posso ajudá-lo?"
      chatMessage="Seja bem-vindo a nossa equipe de suporte. Informe a sua mensagem abaixo:"
      statusMessage="disponível"
      placeholder="Digite a sua mensagem"
      allowClickAway={true}
      allowEsc={true}
    />
  );
}