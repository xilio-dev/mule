<script setup lang="ts">

</script>

<template>

</template>

<style scoped>
.entry-list {
  width: 100%;
  background-color: #fff;
  position: relative;
}

.item {
  transition: all;
}

.entry {
  cursor: pointer;
  position: relative;
  background: #fff;
  padding: 12px 20px 0;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.content-wrapper {
  display: flex;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(228, 230, 235, 0.5);
  width: 100%;
}

.content-wrapper .content-main {
  flex: 1 1 auto;
}

.thumb {
  flex: 0 0 auto;
  width: 108px;
  height: 72px;
  margin-left: 24px;
  background-color: #fff;
  border-radius: 4px;
  border: 1px solid rgba(228, 230, 235, 0.5);
}

.lazy {
  position: relative;
  -o-object-fit: cover;
  object-fit: cover;
}

.lazy :not(.immediate):before {
  transition: opacity .2s;
}

.lazy:before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: inherit;
  border-radius: inherit;
}

.title-row {
  display: flex;
  margin-bottom: 2px;
}

.title {
  font-weight: 600;
  font-size: 16px;
  line-height: 24px;
  color: #252933;
  width: 100%;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}

.jj-link {
  cursor: pointer;
}

.abstract a {
  color: #8a919f;
  font-size: 13px;
  line-height: 22px;
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
}
.entry-footer {
  justify-content: space-between;
  width: 100%;
  flex-wrap: wrap;
}

.entry-footer-tags .entry-footer {
  display: flex;
  flex-direction: row;
  align-items: center;
}


.action-list > .item, .action-list {
  display: flex;
  align-items: center;
}

.footer-tag.width-limited {
  max-width: 65px;
}

.footer-tag.width-limited {
  max-width: 65px;
}

.footer-tag-only {
  max-width: 104px;
}

.footer-tag {
  background-color: #f2f3f5;
  padding: 0 6px;
  border-radius: 2px;
  max-width: 76px;
  box-sizing: border-box;
  margin-left: 6px;
  color: #8a919f;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  min-height: 18px;
  line-height: 18px;
}

.action-list > .item {
  position: relative;
  margin-right: 24px;
  font-size: 13px;
  line-height: 20px;
  color: #8a919f;
  flex-shrink: 0;
}

.action-list > .item, .action-list {
  display: flex;
  align-items: center;
}

.meta-container .user-message {
  max-width: 132px;
  font-size: 13px;
  line-height: 22px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
}

.meta-container .user-message, .meta-container {
  display: flex;
  align-items: center;
  color: #8a919f;
}

.footer-divider {
  width: 1px;
  height: 12px;
  background-color: #e4e6eb;
  margin: 0 -13px 0 12px;
}

.meta-container .user-message .user-popover {
  display: inline;
  overflow: hidden;
  text-overflow: ellipsis;
}

.popover-box {

  align-items: center;
}

.entry-footer-tags, .entry-footer {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.action-list > .item[data-v-5d81f31c] {
  position: relative;
  margin-right: 24px;
  font-size: 13px;
  line-height: 20px;
  color: #8a919f;
  flex-shrink: 0;
}

.action-list > .item, .action-list {
  display: flex;
  align-items: center;
}

.action-list > .item span {
  margin-left: 4px;
}

.action-list > .item span {
  margin-left: 4px;
}

.action-list > .item {
  position: relative;
  margin-right: 24px;
  font-size: 13px;
  line-height: 20px;
  color: #8a919f;
  flex-shrink: 0;
}

.action-list > .item, .action-list {
  display: flex;
  align-items: center;
}

.action-list > .item span {
  margin-left: 4px;
}

action-list > .item.dislike-item {
  margin: 0;
}

.action-list > .item[data-v-5d81f31c] {
  position: relative;

  font-size: 13px;
  line-height: 20px;
  color: #8a919f;
  flex-shrink: 0;
}

.action-list > .item, .action-list {
  display: flex;
  align-items: center;
}

@media screen and (min-width: 961px) {
  .entry .dislike-button {
    display: none;
  }
}

.entry .dislike-button {
  transition: all;
}

.dislike-button {
  display: inline-block;
  cursor: pointer;
}

.dislike-button .icon-close {
  color: #c9cdd4;
  font-weight: bolder;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  height: 100%;
  display: flex;
  align-items: center;
}

svg:not(:root) {
  overflow: hidden;
}

/*@todo dd */
svg {
  width: 16px;
  height: 16px;
  fill: none;
}

svg:not(:root) {
  overflow-clip-margin: content-box;

}
</style>
