import { createPinia } from 'pinia'
import  useUserStore  from './user'
import useFriendStore from './friend'

const pinia = createPinia()

export default pinia
export const UserStore = useUserStore(pinia)
export const FriendStore = useFriendStore(pinia)