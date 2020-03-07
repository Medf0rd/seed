<div id="posts">
    <#list posts as post>
    <div class="card mb-5" data-id="${post.id}">
        <div class="card-body">
            <span class="card-title"><a href="/profile/${post.author.id}" class="text-light text-decoration-none">${post.author.username}</a></span>

            <p class="card-text">${post.text}</p>
            <#list post.tags as tag>
                <span class="card-text"><small class="text-muted">#${tag.name}</small></span>
            </#list>
        </div>
        <#if post.image??>
            <img src="/img/${post.image}" class="card-img-top" alt="image"/>
        </#if>
        <div class="card-footer">
            <a class="text-light" href="/posts/${post.id}/like">
                <#if post.likes?seq_contains(currentUser)>
                    <i class="fas fa-heart "></i>

                <#else>
                    <i class="far fa-heart "></i>
                </#if>
                ${post.likes?size}
            </a>
        </div>
    </div>
    </#list>
</div>
