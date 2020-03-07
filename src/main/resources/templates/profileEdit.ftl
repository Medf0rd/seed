<#import "components/index.ftl" as i>

<@i.page>
<form class="card form-group col-md-6 col-centered" method="post">

    <label for="password">Password</label>
    <div class="input-group input-group-sm mb-3">
        <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-key"></i></span>
        </div>
        <input type="password" name="password" class="form-control form-control-sm" id="password" />
    </div>

    <label for="email">Email</label>
    <div class="input-group input-group-sm mb-3">
        <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-at"></i></span>
        </div>
        <input type="email" name="email" class="form-control form-control-sm" id="email" value="${email!''}"/>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button  type="submit" class="btn btn-primary btn-sm btn-block mb-3">Save</button>

</form>
</@i.page>