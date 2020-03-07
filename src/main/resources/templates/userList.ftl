<#import "components/index.ftl" as i>

<@i.page>
    <table class="table table-striped table-dark">
        <thead>
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Username</th>
            <th scope="col">USER</th>
            <th scope="col">ADMIN</th>
            <th scope="col">Edit</th>
        </tr>
        </thead>
        <tbody>
            <#list users as user>
                <#if user??>
                    <form action="/user" method="post">
                        <tr>
                            <th scope="row">${user.id}</th>
                            <td> <input class="form-control form-control-sm col-md-4 col-centered" type="text" name="username" value="${user.username}"></td>
                            <td class="role"><input type="checkbox" name="USER" ${user.roles?seq_contains('USER')?string("checked", "")}></td>
                            <td class="role"><input type="checkbox" name="ADMIN" ${user.roles?seq_contains('ADMIN')?string("checked", "")}></td>
                            <td><button type="submit" class="btn btn-primary btn-sm">Save</button></td>
                        </tr>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <input type="hidden" name="userId" value="${user.id}"/>
                    </form>
                <#else>
                    <div class="alert alert-danger col-md-6 col-centered" role="alert">
                        User not found
                    </div>
                </#if>
            </#list>

        </tbody>
    </table>

</@i.page>