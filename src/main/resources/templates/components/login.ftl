<#macro login path isRegisterForm>
<form class="card form-group col-md-6 col-centered" action="${path}" method="post">
    <div class="logo">
        <span>Seed</span>
    </div>

    <label for="username">Username</label>
    <div class="input-group input-group-sm mb-3">
        <div class="input-group-prepend">
            <span class="input-group-text">@</span>
        </div>
        <input type="text" class="form-control form-control-sm ${(usernameError??)?string('is-invalid', ' ')}" id="username" name="username" value="<#if user??>${user.username}</#if>"/>
        <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
        </#if>
    </div>

    <label for="password">Password</label>
    <div class="input-group input-group-sm mb-3">
        <div class="input-group-prepend">
            <span class="input-group-text"><i class="fas fa-key"></i></span>
        </div>
        <input type="password" name="password" class="form-control form-control-sm ${(passwordError??)?string('is-invalid', ' ')}" id="password" value="<#if user??>${user.password}</#if>"/>
        <#if passwordError??>
            <div class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>
    </div>

    <#if isRegisterForm>
        <label for="passwordConfirmation">Password confirmation</label>
        <div class="input-group input-group-sm mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text"><i class="fas fa-key"></i></span>
            </div>
            <input type="password" name="passwordConfirmation" class="form-control form-control-sm ${(passwordConfirmationError??)?string('is-invalid', ' ')}" id="passwordConfirmation"/>
            <#if passwordConfirmationError??>
                <div class="invalid-feedback">
                    ${passwordConfirmationError}
                </div>
            </#if>
        </div>

        <label for="email">Email</label>
        <div class="input-group input-group-sm mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text"><i class="fas fa-at"></i></span>
            </div>
            <input type="text" name="email" class="form-control form-control-sm ${(emailError??)?string('is-invalid', ' ')}" id="email" value="<#if user??>${user.email}</#if>"/>
            <#if emailError??>
                <div class="invalid-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
    </#if>


    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button  type="submit" class="btn btn-primary btn-sm btn-block mb-3"><#if isRegisterForm>Sign up<#else>Log in</#if></button>

</form>
<div class="form-inline">
    <div class="form-group card col-md-6 col-centered py-3">
        <span class="">Don't have an account?	&nbsp;</span>
        <#if isRegisterForm>
            <a class="text-decoration-none" href="/login">Log in</a>
        <#else>
            <a class="text-decoration-none" href="/registration">Sign up</a>
        </#if>

    </div>
</div>
</#macro>