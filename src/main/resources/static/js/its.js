$(document).ready(
	() => {
		getAllUsers();
	}
);

const usersTableId = $('#users-table-rows');

function getAllUsers() {
	fetch('/api/users').then(function (response) {
		if (response.ok) {
			response.json().then(users => {
				users.sort((prev, next) => prev.id - next.id);
				usersTableId.empty();
				users.forEach(user => {
					_appendUserRow(user);
				});
			});
		}
	});
}

function _appendUserRow(user) {
	usersTableId
		.append($('<tr class="table table-hover table-bordered">').attr('id', 'userRow[' + user.id + ']')
			.append($('<td>').attr('id', 'userData[' + user.id + '][id]').text(user.id))
			.append($('<td>').attr('id', 'userData[' + user.id + '][fullname]')
				.text(user.firstname + " " + user.lastname))
			.append($('<td>').attr('id', 'userData[' + user.id + '][age]').text(user.age))
			.append($('<td>').attr('id', 'userData[' + user.id + '][email]').text(user.email))
			.append($('<td>').attr('id', 'userData[' + user.id + '][enabled]').text(user.enabled))
			.append($('<td>').attr('id', 'userData[' + user.id + '][roles]').text(user.roles.sort((prev, next) => {
				if (prev.name < next.name) return -1;
				if (prev.name > next.name) return 1;
			}).map(role => {
				return " " + role.name.substr(5);
			})))
			.append($('<td>').append($('<button class="bg-primary btn btn-sm btn-secondary">')
				.click(() => loadUserAndShowModalForm(user.id)).text('Edit')).append(" ")
				.append($('<button class="bg-danger btn btn-sm btn-secondary">')
					.click(() => loadUserAndShowModalForm(user.id, false)).text('Delete'))));
}

const userAddFormId = $('#user-addform');

userAddFormId.find(':submit').click(() => {
	insertUser();
	_eraseUserAddForm();
});

function insertUser() {
	let headers = new Headers();
	headers.append('Content-Type', 'application/json; charset=utf-8');
	let user = {
		'firstname': userAddFormId.find('#newfirstname').val(),
		'lastname': userAddFormId.find('#newlastname').val(),
		'age': userAddFormId.find('#newage').val(),
		'email': userAddFormId.find('#newemail').val(),
		'password': userAddFormId.find('#newpassword').val(),
		'roles': userAddFormId.find('#newroles').val().map(roleId => parseInt(roleId)),
		'enabled': userAddFormId.find('#newenabled').prop('checked')
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

function loadUsersTable() {
	$('#nav-users_table-link').addClass('active');
	$('#nav-users_table').addClass('show').addClass('active');
	$('#nav-user_form-link').removeClass('active');
	$('#nav-user_form').removeClass('show').removeClass('active');
	getAllUsers();
}

function _eraseUserAddForm() {
	userAddFormId.find('#newfirstname').val('');
	userAddFormId.find('#newlastname').val('');
	userAddFormId.find('#newage').val('');
	userAddFormId.find('#newemail').val('');
	userAddFormId.find('#newpassword').val('');
	userAddFormId.find('#newenabled').prop('checked', false);
	userAddFormId.find('#newroles').val('');
}

const userFormId = $('#user-profile');

function loadUserAndShowModalForm(id, editMode = true) {
	fetch('/api/users/' + id, {method: 'GET'})
		.then(function (response) {
			response.json().then(function (user) {
				userFormId.find('#id').val(id);
				userFormId.find('#firstname').val(user.firstname);
				userFormId.find('#lastname').val(user.lastname);
				userFormId.find('#age').val(user.age);
				userFormId.find('#email').val(user.email);
				userFormId.find('#password').val('');
				userFormId.find('#enabled').prop('checked', user.enabled);
				if (editMode) {
					_setReadonlyAttr(false);
					userFormId.find('.modal-title').text('Edit user');
					userFormId.find('#password-div').show();
					userFormId.find('.submit').text('Edit')
						.removeClass('bg-danger btn btn-secondary').addClass('bg-primary btn btn-secondary')
						.removeAttr('onClick').attr('onClick', 'updateUser(' + id + ');');
				} else {
					_setReadonlyAttr();
					userFormId.find('.modal-title').text('Delete user');
					userFormId.find('#password-div').hide();
					userFormId.find('.submit').text('Delete')
						.removeClass('bg-primary btn btn-secondary').addClass('bg-danger btn btn-secondary')
						.removeAttr('onClick').attr('onClick', 'deleteUser(' + id + ');');
				}
				fetch('/api/roles').then(function (response) {
					if (response.ok) {
						userFormId.find('#roles').empty();
						response.json().then(roleList => {
							roleList.sort((prev, next) => next.id - prev.id).forEach(role => {
								userFormId.find('#roles')
									.append($('<option>')
										.prop('selected', user.roles.filter(e => e.id === role.id).length)
										.val(role.id).text(role.name.substr(5)));
							});
						});
					}
				});
				userFormId.modal();
			});
		});
}

function _setReadonlyAttr(value = true) {
	userFormId.find('#firstname').prop('readonly', value);
	userFormId.find('#lastname').prop('readonly', value);
	userFormId.find('#age').prop('readonly', value);
	userFormId.find('#email').prop('readonly', value);
	userFormId.find('#password').prop('readonly', value);
	userFormId.find('#roles').prop('disabled', value);
	userFormId.find('#enabled').prop('disabled', value);
}

function updateUser(id) {
	let headers = new Headers();
	headers.append('Content-Type', 'application/json; charset=utf-8');
	let user = {
		'id': parseInt(userFormId.find('#id').val()),
		'firstname': userFormId.find('#firstname').val(),
		'lastname': userFormId.find('#lastname').val(),
		'age': userFormId.find('#age').val(),
		'email': userFormId.find('#email').val(),
		'password': userFormId.find('#password').val(),
		'roles': userFormId.find('#roles').val().map(roleId => parseInt(roleId)),
		'enabled': userFormId.find('#enabled').prop('checked')
	};
	let request = new Request('/api/users/', {
		method: 'PUT',
		headers: headers,
		body: JSON.stringify(user)
	});
	fetch(request)
		.then(function (response) {
			response.json().then(function (userData) {
				$('#userData\\[' + userData.id + '\\]\\[firstname\\]').text(userData.firstname);
				$('#userData\\[' + userData.id + '\\]\\[lastname\\]').text(userData.lastname);
				$('#userData\\[' + userData.id + '\\]\\[age\\]').text(userData.age);
				$('#userData\\[' + userData.id + '\\]\\[email\\]').text(userData.email);
				$('#userData\\[' + userData.id + '\\]\\[enabled\\]').text(userData.enabled);
				$('#userData\\[' + userData.id + '\\]\\[roles\\]')
					.text(userData.roles.map(role => role.name.substr(5)));
				userFormId.modal('hide');
			});
		});
}

function deleteUser(id) {
	fetch('/api/users/' + id, {method: 'DELETE'})
		.then(function () {
			userFormId.modal('hide');
			usersTableId.find('#userRow\\[' + id + '\\]').remove();
		});
}