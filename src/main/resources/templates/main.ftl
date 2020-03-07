<#import "components/index.ftl" as i>

<@i.page>
    <#if isCurrentUser>
        <#include "components/messageEdit.ftl" />
    </#if>

    <#if posts?has_content>
        <#include "components/messageList.ftl" />
    <#else>
        <div class="alert alert-primary col-md-6 col-centered" role="alert">
            No posts yet
        </div>
    </#if>
<#if isCurrentUser>
    <button type="button" class="btn btn-primary add-btn" data-toggle="modal" data-target="#exampleModal">
        <i class="fas fa-plus fa-2x"></i>
    </button>
</#if>
</@i.page>
<#if post??>
<script type="text/javascript">
            jQuery(document).ready(function(){
                    $('#exampleModal').modal('show')
            });
        </script>
</#if>
