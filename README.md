# SD-Trabalho-01

Trabalho prático da disciplina **Sistemas Distribuídos** – FURB.  
Implementação do **Algoritmo de Exclusão Mútua Centralizado** com simulação de coordenador, fila de requisições e criação dinâmica de processos.

---

## 📌 Especificações da Questão 5

- A cada **1 minuto** o coordenador morre.  
- Ao morrer, a **fila de requisições é reiniciada** e um **novo coordenador é escolhido aleatoriamente**.  
- **Tempo de processamento** de um recurso: **5 a 15 segundos**.  
- **Intervalo entre tentativas** de acesso ao recurso: **10 a 25 segundos**.  
- A cada **40 segundos**, um novo processo é criado com **ID único e randômico**.  
- **IDs duplicados não são permitidos**.  

---

## 👨‍💻 Integrantes

- Bernardo Nicoletti  
- Guilherme Pedroso  
- Maria E Nascimento  

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java (JDK 17+)  
- **Conceitos:** Sistemas Distribuídos, Exclusão Mútua,

---

## 🗓️ Data de entrega

**29/08/2025**

---

## 📖 Observações

* Este projeto simula um **ambiente distribuído simplificado** usando threads para representar processos.
* O coordenador atua como **ponto único de controle** para garantir exclusão mútua.
```
