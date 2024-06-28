<template>
  <div class="admin-list">
    <h2>Liste des Administrateurs</h2>

    <!-- Champ de recherche -->
    <input type="text" v-model="searchQuery" placeholder="Rechercher" @input="filterAdmins" class="form-control mb-3">

    <!-- Tableau des administrateurs -->
    <div class="table-responsive">
      <table class="table table-striped table-bordered">
        <thead>
          <tr>
            <th>Nom</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <!-- Affichage des administrateurs filtrés -->
          <tr v-for="admin in filteredAdmins" :key="admin.id">
            <td>
              <template v-if="!admin.editing">{{ admin.nom }}</template>
              <input v-model="admin.nom" v-else class="form-control">
            </td>
            <td>
              <template v-if="!admin.editing">{{ admin.email }}</template>
              <input v-model="admin.email" v-else class="form-control">
            </td>
            <td>
              <template v-if="!admin.editing">{{ admin.role }}</template>
              <input v-model="admin.role" v-else class="form-control">
            </td>
            <td>
              <template v-if="!admin.editing">
                <button @click="editAdmin(admin)" class="btn btn-sm btn-primary">Modifier</button>
                <button @click="confirmDelete(admin)" class="btn btn-sm btn-danger">Supprimer</button>
                <span v-if="admin.confirmDelete">
                  Confirmer ? 
                  <button @click="deleteAdmin(admin)" class="btn btn-sm btn-danger">Oui</button> 
                  <button @click="cancelDelete(admin)" class="btn btn-sm btn-secondary">Non</button>
                </span>
              </template>
              <template v-else>
                <button @click="saveAdmin(admin)" class="btn btn-sm btn-success">Enregistrer</button>
                <button @click="cancelEdit(admin)" class="btn btn-sm btn-secondary">Annuler</button>
              </template>
            </td>
          </tr>
          <!-- Si aucun résultat ne correspond à la recherche -->
          <tr v-if="filteredAdmins.length === 0 && searchQuery !== ''">
            <td colspan="4" style="text-align: center;">Aucun résultat trouvé.</td>
          </tr>
          <!-- Afficher un message si tous les administrateurs sont visibles -->
          <tr v-if="!searchQuery && admins.length > 0 && filteredAdmins.length === admins.length">
            <td colspan="4" style="text-align: center;">Affichage de tous les administrateurs.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import AdminService from '@/services/AdminService';

export default {
  name: 'ListAdmin',
  data() {
    return {
      admins: [],
      searchQuery: '',
      filteredAdmins: []
    };
  },
  methods: {
    async fetchAdmins() {
      try {
        const response = await AdminService.getAll();
        this.admins = response.data.map(admin => ({
          ...admin,
          editing: false,
          confirmDelete: false
        }));
        this.filteredAdmins = [...this.admins];
      } catch (error) {
        console.error('Erreur lors de la récupération des administrateurs :', error);
      }
    },
    async deleteAdmin(admin) {
      const adminId = admin.id;
      try {
        await AdminService.delete(adminId);
        this.admins = this.admins.filter(a => a.id !== adminId);
        this.filteredAdmins = this.filteredAdmins.filter(a => a.id !== adminId);
      } catch (error) {
        console.error('Erreur lors de la suppression de l\'administrateur :', error);
      }
    },
    confirmDelete(admin) {
      admin.confirmDelete = true;
    },
    cancelDelete(admin) {
      admin.confirmDelete = false;
    },
    editAdmin(admin) {
      admin.editing = true;
    },
    async saveAdmin(admin) {
      try {
        await AdminService.update(admin.id, admin);
        admin.editing = false;
        const index = this.admins.findIndex(a => a.id === admin.id);
        if (index !== -1) {
          this.admins[index] = { ...admin };
          this.filteredAdmins = [...this.admins];
        }
      } catch (error) {
        console.error('Erreur lors de la sauvegarde des modifications de l\'administrateur :', error);
      }
    },
    cancelEdit(admin) {
      admin.editing = false;
    },
    filterAdmins() {
      const query = this.searchQuery.toLowerCase().trim();
      if (query === '') {
        this.filteredAdmins = [...this.admins];
      } else {
        this.filteredAdmins = this.admins.filter(admin =>
          admin.nom.toLowerCase().includes(query) ||
          admin.email.toLowerCase().includes(query) ||
          admin.role.toLowerCase().includes(query)
        );
      }
    }
  },
  created() {
    this.fetchAdmins();
  }
};
</script>

<style scoped>
.admin-list {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(118, 122, 122, 0.2);
  font-family: 'Orbitron', sans-serif;
  color: rgb(60, 67, 67);
}

h2 {
  color: #444;
}

.table-responsive {
  margin-top: 20px;
}

.table th {
  background-color: #b6c7d9;
  color: #fff;
}

.table-striped tbody tr:nth-of-type(odd) {
  background-color: rgba(0, 0, 0, 0.5);
}

.table td {
  color: rgb(15, 16, 16);
}

.btn {
  cursor: pointer;
  transition: background 0.3s, color 0.3s;
}

.btn-primary {
  background-color: rgb(0, 123, 255);
  color: #fff;
}

.btn-primary:hover {
  background-color: rgb(23, 101, 237);
}

.btn-danger {
  background-color: rgb(220, 53, 69);
  color: #fff;
}

.btn-danger:hover {
  background-color: rgb(185, 28, 49);
}

.btn-success {
  background-color: rgb(40, 167, 69);
  color: #fff;
}

.btn-success:hover {
  background-color: rgb(30, 140, 60);
}

.btn-secondary {
  background-color: rgb(108, 117, 125);
  color: #fff;
}

.btn-secondary:hover {
  background-color: rgb(80, 89, 97);
}

.form-control {
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 15px;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.form-control:focus {
  color: #495057;
  background-color: #fff;
  border-color: #80bdff;
  outline: 0;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
</style>
