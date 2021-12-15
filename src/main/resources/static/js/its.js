$(document).ready(
    () => {
        getAllUsers();
        initNavigation();
    }
);

const usersTableId = $('#users-table-rows');
const userFormId = $('#user-profile');
const userAddFormId = $('#user-addform');

$('#nav-users_table-link').click(() => {
    loadUsersTable();
});
$('#nav-user_form-link').click(() => {
    loadAddForm();
});
userAddFormId.find(':submit').click(() => {
    insertUser();
});

function loadUsersTable() {
    $('#nav-users_table-link').addClass('active');
    $('#nav-users_table').addClass('show').addClass('active');
    $('#nav-user_form-link').removeClass('active');
    $('#nav-user_form').removeClass('show').removeClass('active');
    getAllUsers();
}

function initNavigation() {
    $('#admin-area-tab').click(() => {
        $('#admin-area-tab').addClass('active').removeClass('btn-light').addClass('btn-primary').prop('aria-selected', true);
        $('#admin-area').addClass('active');
        $('#user-area-tab').removeClass('active').removeClass('btn-primary').addClass('btn-light').prop('aria-selected', false);
        $('#user-area').removeClass('active');
    });
    $('#user-area-tab').click(() => {
        $('#user-area-tab').addClass('active').removeClass('btn-light').addClass('btn-primary').prop('aria-selected', true);
        $('#user-area').addClass('active');
        $('#admin-area-tab').removeClass('active').removeClass('btn-primary').addClass('btn-light').prop('aria-selected', false);
        $('#admin-area').removeClass('active');
    });
}

function loadAddForm() {
    $('#nav-user_form-link').addClass('active');
    $('#nav-user_form').addClass('show').addClass('active');
    $('#nav-users_table-link').removeClass('active');
    $('#nav-users_table').removeClass('show').removeClass('active');
    loadUserForInsertForm();
}

function getAllUsers() {
    fetch('/api/users').then(function (response) {
        if (response.ok) {
            response.json().then(users => {
                usersTableId.empty();
                users.forEach(user => {
                    _appendUserRow(user);
                });
            });
        }
    });
}

function insertUser() {
    _eraseUserAddForm();

    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let user = {
        'firstname': userAddFormId.find('#newfirstname').val(),
        'lastname': userAddFormId.find('#newlastname').val(),
        'age': userAddFormId.find('#newage').val(),
        'email': userAddFormId.find('#newemail').val(),
        'password': userAddFormId.find('#newpassword').val(),
        'roles': userAddFormId.find('#newroles').val().map(roleId => parseInt(roleId))
    };
    let request = new Request('/api/users/', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });

    fetch(request)
        .then(function (response) {
            response.json().then(function (userData) {
                console.log(userData);
                loadUsersTable();
            });
        });
}

function loadUserForInsertForm() {
    _eraseUserAddForm();
    userAddFormId.find('#newfirstname').val('');
    userAddFormId.find('#newlastname').val('');
    userAddFormId.find('#newage').val('');
    userAddFormId.find('#newemail').val('');
    userAddFormId.find('#newpassword').val('');

    fetch('/api/roles').then(function (response) {
        if (response.ok) {
            userAddFormId.find('#newroles').empty();
            response.json().then(roleList => {
                roleList.forEach(role => {
                    userAddFormId.find('#newroles')
                        .append($('<option>').val(role.id).text(role.name));
                });
            });
        }
    });
}

function loadUserAndShowModalForm(id, editMode = true) {
    _eraseUserModalForm();

    fetch('/api/users/' + id, {method: 'GET'})
        .then(function (response) {
                response.json().then(function (user) {
                    userFormId.find('#id').val(id);
                    userFormId.find('#firstname').val(user.firstname);
                    userFormId.find('#lastname').val(user.lastname);
                    userFormId.find('#age').val(user.age);
                    userFormId.find('#email').val(user.email);
                    userFormId.find('#password').val('');
                    if (editMode) {
                        userFormId.find('.modal-title').text('Edit user');
                        userFormId.find('#password-div').show();
                        userFormId.find('.submit').text('Edit').removeClass('btn-danger').addClass('btn-primary')
                            .removeAttr('onClick')
                            .attr('onClick', 'updateUser(' + id + ');');
                        _setReadonlyAttr(false);
                    } else {
                        userFormId.find('.modal-title').text('Delete user');
                        userFormId.find('#password-div').hide();
                        userFormId.find('.submit').text('Delete').removeClass('btn-primary').addClass('btn-danger')
                            .removeAttr('onClick')
                            .attr('onClick', 'deleteUser(' + id + ');');
                        _setReadonlyAttr();
                    }

                    fetch('/api/roles').then(function (response) {
                        if (response.ok) {
                            userFormId.find('#roles').empty();
                            response.json().then(roleList => {
                                roleList.forEach(role => {
                                    userFormId.find('#roles')
                                        .append($('<option>')
                                            .prop('selected', user.roles.filter(e => e.id === role.id).length)
                                            .val(role.id).text(role.name));
                                });
                            });
                        }
                    });
                    userFormId.modal();
                });
            }
        )
}

function deleteUser(id) {
    fetch('/api/users/' + id, {method: 'DELETE'})
        .then(function () {
            userFormId.modal('hide');
            usersTableId.find('#userRow\\[' + id + '\\]').remove();
        });
}

function updateUser(id) {
    _eraseUserModalForm();

    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let user = {
        'id': parseInt(userFormId.find('#id').val()),
        'firstname': userFormId.find('#firstname').val(),
        'lastname': userFormId.find('#lastname').val(),
        'age': userFormId.find('#age').val(),
        'email': userFormId.find('#email').val(),
        'password': userFormId.find('#password').val(),
        'roles': userFormId.find('#roles').val().map(roleId => parseInt(roleId))
    };
    let request = new Request('/api/users/', {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(user)
    });

    fetch(request)
        .then(function (response) {
            response.json().then(function (userData) {
                $('#userData\\[' + userData.id + '\\]\\[firstname\\]').text(userData.firstname)
                $('#userData\\[' + userData.id + '\\]\\[lastname\\]').text(userData.lastname)
                $('#userData\\[' + userData.id + '\\]\\[age\\]').text(userData.age)
                $('#userData\\[' + userData.id + '\\]\\[email\\]').text(userData.email)
                $('#userData\\[' + userData.id + '\\]\\[roles\\]').text(userData.roles.map(role => role.name))
                userFormId.modal('hide');
            });
        })
}

function _appendUserRow(user) {
    usersTableId
        .append($('<tr class="border-top bg-light">').attr('id', 'userRow[' + user.id + ']')
            .append($('<td>').attr('id', 'userData[' + user.id + '][id]').text(user.id))
            .append($('<td>').attr('id', 'userData[' + user.id + '][firstname]').text(user.firstname))
            .append($('<td>').attr('id', 'userData[' + user.id + '][lastname]').text(user.lastname))
            .append($('<td>').attr('id', 'userData[' + user.id + '][age]').text(user.age))
            .append($('<td>').attr('id', 'userData[' + user.id + '][email]').text(user.email))
            .append($('<td>').attr('id', 'userData[' + user.id + '][roles]').text(user.roles.map(role => role.name)))
            .append($('<td>').append($('<button class="btn btn-sm btn-info">')
                .click(() => {
                    loadUserAndShowModalForm(user.id);
                }).text('Edit')))
            .append($('<td>').append($('<button class="btn btn-sm btn-danger">')
                .click(() => {
                    loadUserAndShowModalForm(user.id, false);
                }).text('Delete')))
        );
}

function _setReadonlyAttr(value = true) {
    userFormId.find('#firstname').prop('readonly', value);
    userFormId.find('#lastname').prop('readonly', value);
    userFormId.find('#age').prop('readonly', value);
    userFormId.find('#email').prop('readonly', value);
    userFormId.find('#password').prop('readonly', value);
    userFormId.find('#roles').prop('disabled', value);
}

function _eraseUserAddForm() {
    userAddFormId.find('.invalid-feedback').remove();
    userAddFormId.find('#newfirstname').removeClass('is-invalid');
    userAddFormId.find('#newage').removeClass('is-invalid');
    userAddFormId.find('#newemail').removeClass('is-invalid');
    userAddFormId.find('#newpassword').removeClass('is-invalid');
}

function _eraseUserModalForm() {
    userFormId.find('.invalid-feedback').remove();
    userFormId.find('#firstname').removeClass('is-invalid');
    userFormId.find('#email').removeClass('is-invalid');
    userFormId.find('#password').removeClass('is-invalid');
    userFormId.find('#age').removeClass('is-invalid');
}





