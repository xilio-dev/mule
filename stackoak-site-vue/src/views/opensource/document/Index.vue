<script setup lang="ts">
import Navbar from "@/components/Navbar.vue";
</script>

<template>

  <div class="main-container">
    <div class="content-wrapper">
      <div class="document-container">
        <div class="document-header">
          <h1 class="document-title">欢迎来到StackOak文档中心</h1>
          <p class="document-description">
            欢迎来到StackOak文档中心！在这里，您可以找到关于 StackOak网站 的全面文档和资源，帮助您快速上手并深入了解我们的产品和服务。
          </p>
          <p class="document-description">让我们一起开启您的旅程吧！</p>
        </div>
        <ul class="document-list">
          <li class="document-list-item">
            <h2 class="document-category">StackOak社区文档</h2>
            <div class="document-category-items">
              <div
                  class="document-category-item"
                  v-for="i in 2"
                  :key="i">
                <RouterLink to="/opensource/document/detail" style="text-decoration: none;" target="_blank">
                  <a class="document-category-item-link">
                    <img
                        src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB8AAAAlCAYAAAC6TzLyAAAAAXNSR0IArs4c6QAAA1NJREFUWEfFl01MU0EQx//zXlsE1BYUE+gFCDHRSIiJHuTgR8LFkxdPJoaLCVETIhATL5oaYxQj1KtH795MNCAf0YAGNAgBQa0tRVqUDwstpaW0761ZoLSvfaUf1Ly97ZvZ+c3s7szbIcSPLnsLQI0ATgAQFbJ0E5/HgS/DSzAsXIDFEkinzuW0qfT670GI3ikIQgVkOZN1yTpejwOjw9UAZBiYEZab/nSGtuDd024A5WBsa57LiMEBBoaCiAmWZt9upgjd9laQ2JFzxFHr8fDoN0k0ob3Jm8oBQpdjFEBdLsEq1qjBuYIhVAJLy4qafaIuRyTry6VmKRIOssHeQtUgZJTi8Y3lRBnpu6fZnqPmBkQdwr2veCA6VXuG9UOwtHriZVTc48wPnAiBgR4fC4UOqsIFEZBCR/CoeTEqJ1PfTH7g3KIkB1b63hSCtlM40YsEB6isf1YB5xODQDDqBPCcSTfCMuCNxGqD3zb5M+i016RcJwiAjspgaVoi8ztXEsF11pyOqZAfHZxDQIqZ8dmmbKt2WzUoRZXkDkiRw1T53q2AF4uEifryrOCnh+axuCHt7DU3KIDCrsF+f8S/WshkqWCnmsYsj1PNwFxS5CV6AScP6MHS7zqWwjLG/WFVZ3m5JL0e3FA4GFyj+HMkgI59+JMBIquNyFiZ6j7Oawc/NbSgHbz+06J28HOflxTwCAPuVu3H+RJDBlm++/Hy7H/i9KN/eUO16lDDiEcBN+oIL2tNGV+adIphBjSMeDYLV9KP5eLoclLk96uKccaoT2c3I3nnbABvPSkivzS2ot2ZXx73age/8nVVO3jjpF87+LVva0lwgwBUFgh7TjV+v38EUz/F6fr3gAJeIADWGvWnWEbXO0FpJcJw274OvcqjnJptQQWc6zyr2ZcLR3XN3AbDw5kQdGrwNnsoadu5l0UC7XnbuTerEu8g1AfdcWxod+HuOXkB1GbQg1/8V6LNoPZZmdOza4fz4CsBEnW4WX56tewdGqNOF2sTRDxlObbl2TMBEgAmoW0z+6xu5iZCOWMpOo1cCKnSi8AYw+8WM5k34c89zBgIYJJEVPzPHdiOeK6oCMebSsmrqDtWF2sF4SqA2ry0zbHoeT8zIRBe3Koga/TzP0gjR1868eQqAAAAAElFTkSuQmCC"
                        alt="大数据开发技术"/>
                    StackOak社区使用文档
                  </a>
                </RouterLink>
              </div>
            </div>
          </li>

          <li class="document-list-item">
            <h2 class="document-category">大数据开发</h2>
            <div class="document-category-items">
              <div
                  class="document-category-item"
                  v-for="i in 2"
                  :key="i">
                <RouterLink to="/opensource/document/detail" style="text-decoration: none;" target="_blank">
                  <a class="document-category-item-link">
                    <img
                        src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB8AAAAlCAYAAAC6TzLyAAAAAXNSR0IArs4c6QAAA1NJREFUWEfFl01MU0EQx//zXlsE1BYUE+gFCDHRSIiJHuTgR8LFkxdPJoaLCVETIhATL5oaYxQj1KtH795MNCAf0YAGNAgBQa0tRVqUDwstpaW0761ZoLSvfaUf1Ly97ZvZ+c3s7szbIcSPLnsLQI0ATgAQFbJ0E5/HgS/DSzAsXIDFEkinzuW0qfT670GI3ikIQgVkOZN1yTpejwOjw9UAZBiYEZab/nSGtuDd024A5WBsa57LiMEBBoaCiAmWZt9upgjd9laQ2JFzxFHr8fDoN0k0ob3Jm8oBQpdjFEBdLsEq1qjBuYIhVAJLy4qafaIuRyTry6VmKRIOssHeQtUgZJTi8Y3lRBnpu6fZnqPmBkQdwr2veCA6VXuG9UOwtHriZVTc48wPnAiBgR4fC4UOqsIFEZBCR/CoeTEqJ1PfTH7g3KIkB1b63hSCtlM40YsEB6isf1YB5xODQDDqBPCcSTfCMuCNxGqD3zb5M+i016RcJwiAjspgaVoi8ztXEsF11pyOqZAfHZxDQIqZ8dmmbKt2WzUoRZXkDkiRw1T53q2AF4uEifryrOCnh+axuCHt7DU3KIDCrsF+f8S/WshkqWCnmsYsj1PNwFxS5CV6AScP6MHS7zqWwjLG/WFVZ3m5JL0e3FA4GFyj+HMkgI59+JMBIquNyFiZ6j7Oawc/NbSgHbz+06J28HOflxTwCAPuVu3H+RJDBlm++/Hy7H/i9KN/eUO16lDDiEcBN+oIL2tNGV+adIphBjSMeDYLV9KP5eLoclLk96uKccaoT2c3I3nnbABvPSkivzS2ot2ZXx73age/8nVVO3jjpF87+LVva0lwgwBUFgh7TjV+v38EUz/F6fr3gAJeIADWGvWnWEbXO0FpJcJw274OvcqjnJptQQWc6zyr2ZcLR3XN3AbDw5kQdGrwNnsoadu5l0UC7XnbuTerEu8g1AfdcWxod+HuOXkB1GbQg1/8V6LNoPZZmdOza4fz4CsBEnW4WX56tewdGqNOF2sTRDxlObbl2TMBEgAmoW0z+6xu5iZCOWMpOo1cCKnSi8AYw+8WM5k34c89zBgIYJJEVPzPHdiOeK6oCMebSsmrqDtWF2sF4SqA2ry0zbHoeT8zIRBe3Koga/TzP0gjR1868eQqAAAAAElFTkSuQmCC"
                        alt="Flink开发指南"/>
                    Flink开发指南
                  </a>
                </RouterLink>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.main-container {
  box-sizing: border-box;
}

.content-wrapper {
  flex: 1;
  margin-left: 12px;
  border-radius: 6px;
  overflow: hidden;
}

.document-container {
  background-color: #f7f7fa;
  background-image: url(@/assets/doc_back.png);
  background-size: 360px auto;
  background-position: right top;
  background-repeat: no-repeat;
  padding: 45px;
}

.document-header {
  width: 62%;
}

.document-title {
  font-size: 40px;
  margin-bottom: 34px;
  color: #000;
  font-weight: 700;
}

.document-description {
  font-size: 16px;
  color: #6c6f80;
  font-weight: 400;
  line-height: 24px;
}

.document-description:not(:last-child) {
  margin-bottom: 8px;
}

.document-list {
  margin-top: 100px;
  padding-left: 0;
}

.document-container .document-list li:not(:last-child) {
  margin-bottom: 50px;
}

.document-list-item {
  list-style-type: none;
}

.document-category {
  font-size: 24px;
  font-weight: 700;
  color: #000;
  margin-bottom: 18px;
}

.document-category-items {
  display: flex;
  flex-wrap: wrap;
}

.document-category-item {
  width: 50%;
  box-sizing: border-box;
  margin-bottom: 12px;
}

.document-category-item:nth-child(odd) {
  padding-right: 12px;
}

.document-category-item:hover {
  transition: box-shadow 0.3s ease;
  box-shadow: 0 0 50px rgba(43, 41, 41, 0.1);
}

.document-category-item-link {
  background-color: #fff;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  border-radius: 4px;
  padding: 30px;
  font-size: 20px;
  font-weight: 700;
  color: #000;
  text-decoration: none;
}

.document-category-item-link img {
  display: block;
  width: 24px;
  margin-right: 12px;
}
</style>
