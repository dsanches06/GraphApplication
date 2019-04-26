# GraphApplication
Aplicação Didática sobre Grafos

### Descrição
Pretende-se desenvolver uma aplicação didática sobre grafos não-orientados. Esta aplicação será capaz de importar um grafo de um ficheiro, visualizá-lo, manipulá-lo, obter informações sobre o mesmo e exportar o grafo resultante.
A implementação do grafo utilizado deverá respeitar a interface Graph<V,E> lecionada da unidade curricular.
Para efeitos desta aplicação os tipos de dados a guardar são:
•  Vértices: string
•  Aresta: tuplo {string, custo}
O custo é um valor inteiro
A aplicação deverá ser capaz de importar / exportar grafos de (i) ficheiros de texto e (ii) JSON. A aplicação deve ser capaz de importar qualquer mapa exportado pela aplicação nestes dois formatos.
•  É fornecido com este enunciado um ficheiro de texto cujo formato deve ser respeitado.
•  O formato JSON é “livre” na sua estrutura.
As opções de importar / exportar devem estar presentes num menu de aplicação (barra de menu).
O grafo importado deve ser visualizado graficamente na aplicação. Deve ser mostrado adicionalmente (e constantemente) a contagem atual de vértices, vértices isolados e arestas. Deve ser possível adicionar/remover vértices e arestas ao grafo. A remoção de um vértice implica a remoção das suas arestas incidentes. A todo instante a visualização e as contagens devem acompanhar a manipulação efetuada.
Dado um vértice de origem, deve ser mostrado o resultado de percorrer o grafo em:
• Largura, e;
• Profundidade.
Dado um vértice de origem, deve ser aplicado o algoritmo Dijkstra e visualizado:
• O resultado do mesmo em forma de tabela, i.e., custos e predecessor (vértices e arestas);
• O caminho completo do vértice de origem para todos os restantes (vértices e arestas).
Caminhos impossíveis deverão ser indicados com a palavra “impossível”.
Em relação ao ponto anterior, deve ser possível “desfazer” qualquer manipulação efetuada até ao estado do grafo aquando da sua importação.
A utilização de padrões de software é essencial no desenvolvimento e cumprimento dos requisitos
funcionais e anteriores.
É esperado que se utilizem alguns dos padrões de software lecionados na unidade curricular, sendo
a sua aplicabilidade a cada requisito funcional da competência do(s) aluno(s) programador(es).
O projeto será desenvolvido  com interface gráfica em JavaFX.

### Ambiente Tecnologico
Java SE, Netbeans, Padrões de Software, JavaFX

#### Galeria de Imagens
![](/graph_application.jpg)
![](/graph_estatistica.png)

