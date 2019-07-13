<template>
  <div class="dashboard">
    <main-header/>
    <div class="dashboard-main-container">
    <dashboard-main-section/>
    <dashboard-quick-link-section/>
    </div>
    <main-footer/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';
import MainHeader from "../components/Header";
import MainFooter from "../components/Footer";
import DashboardMainSection from "../components/dashboard/DashboardMainSection";
import DashboardQuickLinkSection from "../components/dashboard/DashboardQuickLinkSection";

export default {
  name: 'dashboard',
  components: {
    DashboardQuickLinkSection,
    DashboardMainSection,
    MainFooter,
    MainHeader,
    HelloWorld,
  },
};
</script>

<style lang="scss">
  .dashboard-main-container {
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
  }
</style>
